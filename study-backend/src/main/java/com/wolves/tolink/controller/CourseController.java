package com.wolves.tolink.controller;

import com.wolves.tolink.dto.*;
import com.wolves.tolink.entity.Role;
import com.wolves.tolink.framework.common.response.QueryResult;
import com.wolves.tolink.framework.common.response.ResponseData;
import com.wolves.tolink.service.CourseService;
import com.wolves.tolink.service.TeacherService;
import com.wolves.tolink.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
/**
 * @author xulu
 */
public class CourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private UserService userService;

    @ApiOperation(value="获取课程列表", notes="获取课程列表")
    @GetMapping(value = "/list")
    public ResponseData listClazz(){
        ResponseData result = new ResponseData();
        result.setCode(0);
        result.setMsg("success");
        result.setData(courseService.getAll());
        return result;
    }

    @ApiOperation(value="获取条件搜索", notes="获取条件搜索")
    @PostMapping(value = "/query")
    public ResponseData query(@RequestHeader("Authorization") String token,
                              @RequestBody CourseQueryDTO dto){
        ResponseData result = new ResponseData();
        UserDTO user;
        try{
            user = userService.selectByToken(token);
        } catch (Exception e){
            result.setCode(-1);
            result.setMsg(e.getMessage());
            return result;
        }
        //如果是管理员可以看到所有学生，如果是老师只能看到自己的学生
        Role role = user.getRole();
        if("teacher".equals(role.getRoleName())){
            String teacherId = teacherService.getByUserId(user.getId()).getId();
            dto.setTeacherId(teacherId);
        }
        Integer page = dto.getPage();
        if(null == page || page < 1){
            page = 1;
        }
        Integer size = dto.getSize();
        if(null == size || size < 1){
            size = 10;
        }
        dto.setSize(size);
        String orderColumn = dto.getOrderColumn();
        if(StringUtils.isEmpty(orderColumn)){
            result.setCode(-1);
            result.setMsg("排序字段orderColumn不能为空");
            return result;
        }
        if(!"id,age,score,create_time".contains(orderColumn)){
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
        if(!"desc,asc".contains(orderIndex)){
            result.setCode(-1);
            result.setMsg("排序方式有误");
            return result;
        }
        dto.setPage((page - 1) * size);
        QueryResult<CourseResultDTO> queryResult = courseService.getByCondition(dto);
        result.setCode(0);
        result.setMsg("success");
        result.setData(queryResult);
        return result;
    }

    @ApiOperation(value="根据教师id获取课程列表", notes="根据教师id获取课程列表")
    @GetMapping(value = "/teacher")
    public ResponseData getByTeacherId(@RequestParam("id") String id){
        ResponseData result = new ResponseData();
        if(null == teacherService.getById(id)){
            result.setCode(-1);
            result.setMsg("id有误,该教师不存在");
            return result;
        }
        result.setCode(0);
        result.setMsg("success");
        result.setData(courseService.getByTeacherId(id));
        return result;
    }

    @ApiOperation(value="教师身份登陆获取自己所教课程", notes="教师身份登陆获取自己所教课程")
    @GetMapping(value = "/own")
    public ResponseData own(@RequestHeader("Authorization") String token){
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
        String teacherId;
        if("teacher".equals(role.getRoleName())){
             teacherId = teacherService.getByUserId(user.getId()).getId();
        } else {
            result.setCode(-1);
            result.setMsg("您不是教师");
            return result;
        }
        result.setCode(0);
        result.setMsg("success");
        result.setData(courseService.getByTeacherId(teacherId));
        return result;
    }

}
