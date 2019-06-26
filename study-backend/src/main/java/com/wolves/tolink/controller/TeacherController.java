package com.wolves.tolink.controller;

import com.wolves.tolink.dto.*;
import com.wolves.tolink.dto.TeaQueryDTO;
import com.wolves.tolink.dto.TeacherDTO;
import com.wolves.tolink.dto.UserDTO;
import com.wolves.tolink.entity.Role;
import com.wolves.tolink.framework.common.response.ResponseData;
import com.wolves.tolink.service.TeacherService;
import com.wolves.tolink.service.UserService;
import com.wolves.tolink.dto.AddTeacherDTO;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teacher")
/**
 * @author xulu
 */
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private UserService userService;

    @ApiOperation(value="创建教师", notes="创建教师")
    @PostMapping(value = "/add")
    public ResponseData add(@RequestHeader("Authorization") String token,
                            @RequestBody AddTeacherDTO dto){
        ResponseData result = new ResponseData();
        UserDTO user;
        try{
            user = userService.selectByToken(token);
        } catch (Exception e){
            result.setCode(-1);
            result.setMsg(e.getMessage());
            return result;
        }

        Role role = user.getRole();
        if(!"admin".equals(role.getRoleName())){
            result.setCode(-1);
            result.setMsg("您没有创建权限");
            return result;
        }
        try{
            teacherService.addTeacher(dto);
        } catch (Exception e){
            result.setCode(-1);
            result.setMsg(e.getMessage());
            return result;
        }
        result.setCode(0);
        result.setMsg("success");
        return result;
    }

    @ApiOperation(value="获取教师列表", notes="获取教师列表")
    @PostMapping(value = "/list")
    public ResponseData listTeacher(@RequestHeader("Authorization") String token,
                                    @RequestBody TeaQueryDTO dto){
        ResponseData result = new ResponseData();
        UserDTO user;
        try{
            user = userService.selectByToken(token);
        } catch (Exception e){
            result.setCode(-1);
            result.setMsg(e.getMessage());
            return result;
        }

        //获取user的角色，根据角色构造筛选条件
        Role role = user.getRole();
        if("teacher".equals(role.getRoleName())){
            String teacherId = teacherService.getByUserId(user.getId()).getId();
            dto.setTeacherId(teacherId);
        }
        Integer page = dto.getPage();
        Integer size = dto.getSize();
        if(null == page || page < 1){
            page = 1;
        }

        if(null == size || size < 1){
            size = 10;
        }
        String orderColumn = dto.getOrderColumn();
        if(StringUtils.isEmpty(orderColumn)){
            result.setCode(-1);
            result.setMsg("排序字段orderColumn不能为空");
            return result;
        }
        if(!"id,create_time".contains(orderColumn)){
            result.setCode(-1);
            result.setMsg("排序字段有误");
            return result;
        }
        String orderIndex = dto.getOrderIndex();
        if(StringUtils.isEmpty(orderIndex)){
            result.setCode(-1);
            result.setMsg("排序方式orderIndex不能为空");
            return result;
        }
        if(!"asc,desc".contains(dto.getOrderIndex())){
            result.setCode(-1);
            result.setMsg("排序方式有误");
            return result;
        }
        dto.setPage((page - 1) * size);
        dto.setSize(size);
        result.setCode(0);
        result.setMsg("success");
        result.setData(teacherService.getByCondition(dto));
        return result;
    }

    @ApiOperation(value="教师详细信息", notes="教师详细信息")
    @GetMapping(value = "/detail")
    public ResponseData listStudent(@RequestHeader("Authorization") String token,
                                    @RequestParam String id){
        ResponseData result = new ResponseData();
        UserDTO user;
        try{
            user = userService.selectByToken(token);
        } catch (Exception e){
            result.setCode(-1);
            result.setMsg(e.getMessage());
            return result;
        }
        //获取user的角色，根据角色构造筛选条件

        TeacherDTO teacher = teacherService.getDTOById(id);
        result.setCode(0);
        result.setMsg("success");
        result.setData(teacher);
        return result;
    }
}
