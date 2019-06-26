package com.wolves.tolink.controller;

import com.wolves.tolink.dto.*;
import com.wolves.tolink.dto.*;
import com.wolves.tolink.entity.Role;
import com.wolves.tolink.entity.Teacher;
import com.wolves.tolink.framework.common.response.QueryResult;
import com.wolves.tolink.framework.common.response.ResponseData;
import com.wolves.tolink.service.StudentService;
import com.wolves.tolink.service.TeacherService;
import com.wolves.tolink.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/student")
/**
 * @author xulu
 */
public class StudentController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StudentService studentService;
    @Autowired
    private UserService userService;
    @Autowired
    private TeacherService teacherService;

    @ApiOperation(value="创建学生信息", notes="创建学生信息")
    @PostMapping(value = "/add")
    public ResponseData<String> add(@RequestHeader("Authorization") String token,
                                    @RequestBody AddStudentDTO dto){
        ResponseData result = new ResponseData();
        UserDTO user;
        try{
            user = userService.selectByToken(token);
        } catch (Exception e){
            result.setCode(-1);
            result.setMsg(e.getMessage());
            return result;
        }
        if(!"teacher".equals(user.getRole().getRoleName())){
            result.setCode(-1);
            result.setMsg("只有教师拥有权限");
            return result;
        }
        //获取teacher
        Teacher teacher = teacherService.getByUserId(user.getId());
        try{
            studentService.add(dto, teacher);
        } catch (Exception e){
            result.setCode(-1);
            result.setMsg(e.getMessage());
            return result;
        }
        result.setCode(0);
        result.setMsg("success");
        return result;
    }

    @ApiOperation(value="获取学生列表", notes="获取学生列表")
    @PostMapping(value = "/list")
    public ResponseData listStudent(@RequestHeader("Authorization") String token,
                                    @RequestBody StuQueryDTO dto){
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
        if(!"id,age,avg_score,total_score,total_course,entry_time,create_time".contains(orderColumn)){
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
        QueryResult<StudentViewDTO> queryResult = studentService.getByCondition(dto);
        result.setCode(0);
        result.setMsg("success");
        result.setData(queryResult);
        return result;
    }

    @ApiOperation(value="学生详细信息", notes="学生详细信息")
    @GetMapping(value = "/detail")
    public ResponseData listStudent(@RequestHeader("Authorization") String token,
                                    @RequestParam("id") String id){
        ResponseData result = new ResponseData();
        UserDTO user;
        try{
            user = userService.selectByToken(token);
        } catch (Exception e){
            result.setCode(-1);
            result.setMsg(e.getMessage());
            return result;
        }
        if(StringUtils.isEmpty(id)){
            result.setCode(-1);
            result.setMsg("id不能为空");
            return result;
        }
        StudentDTO student;
        try{
            student = studentService.getById(id);
        } catch (Exception e){
            result.setCode(-1);
            result.setMsg(e.getMessage());
            return result;
        }
        result.setCode(0);
        result.setMsg("success");
        result.setData(student);
        return result;
    }

    @ApiOperation(value="删除学生信息", notes="删除学生信息")
    @PostMapping(value = "/delete")
    public ResponseData<String> delete(@RequestBody List<String> ids){
        ResponseData result = new ResponseData();
        for(String id : ids){
            try{
                studentService.deleteById(id);
            } catch (Exception e){
                logger.error("删除学生异常", e);
            }
        }
        result.setCode(0);
        result.setMsg("success");
        return result;
    }

    @ApiOperation(value="修改学生信息", notes="修改学生信息")
    @PostMapping(value = "/updateStu")
    public ResponseData updateStu(@RequestHeader("Authorization") String token,
                                  @RequestBody UpdateStudentDTO updateStuDTO){
        ResponseData result = new ResponseData();
        UserDTO user;
        try{
            user = userService.selectByToken(token);
        } catch (Exception e){
            result.setCode(-1);
            result.setMsg(e.getMessage());
            return result;
        }

        StudentDTO student = studentService.getById(updateStuDTO.getId());
        if(null == student){
            result.setCode(-1);
            result.setMsg("id有误，该学生不存在");
            return result;
        }
        try{
            studentService.updateStu(updateStuDTO);
        } catch (Exception e){
            result.setCode(-1);
            result.setMsg(e.getMessage());
            return result;
        }
        result.setCode(0);
        result.setMsg("success");
        return result;
    }

}
