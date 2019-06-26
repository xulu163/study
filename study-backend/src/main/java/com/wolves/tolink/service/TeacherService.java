package com.wolves.tolink.service;

import com.wolves.tolink.dto.AddTeacherDTO;
import com.wolves.tolink.dto.TeaQueryDTO;
import com.wolves.tolink.dto.TeacherDTO;
import com.wolves.tolink.dto.TeacherViewDTO;
import com.wolves.tolink.entity.*;
import com.wolves.tolink.framework.common.response.QueryResult;
import com.wolves.tolink.mapper.TeacherCourseMapper;
import com.wolves.tolink.mapper.TeacherMapper;
import com.wolves.tolink.util.IdUtil;
import com.wolves.tolink.entity.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author xulu
 */
@Service
public class TeacherService {

    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    TeacherCourseMapper teacherCourseMapper;
    @Autowired
    SchoolService schoolService;
    @Autowired
    ClazzService clazzService;
    @Autowired
    CourseService courseService;
    @Autowired
    UserService userService;
    @Autowired
    StudentService studentService;

    public QueryResult<TeacherViewDTO> getByCondition(TeaQueryDTO dto){
        //teacherId为空表示获取所有
        List<TeacherViewDTO> list = teacherMapper.selectByCondition(dto);
        Integer totalStu = 0;
        Integer totalCourse = 0;
        for(TeacherViewDTO view : list){
            totalStu = studentService.getTotalByTeacher(view.getId());
            List<Course> tealist = courseService.getByTeacherId(view.getId());
            if(!CollectionUtils.isEmpty(tealist)){
                totalCourse = tealist.size();
            }
            view.setTotalStu(totalStu);
            view.setTotalCourse(totalCourse);
        }
        Integer count = teacherMapper.selectCountByCondition(dto);

        QueryResult<TeacherViewDTO> result = new QueryResult<TeacherViewDTO>();
        result.setRows(list);
        result.setTotal(count);
        return result;

    }

    public Teacher getById(String id){
        return teacherMapper.selectByPrimaryKey(id);
    }

    public TeacherDTO getDTOById(String id){
        Teacher teacher = teacherMapper.selectByPrimaryKey(id);

        TeacherDTO teacherDTO = new TeacherDTO();
        BeanUtils.copyProperties(teacher, teacherDTO);

        School school = schoolService.getById(teacher.getSchoolId());
        teacherDTO.setSchool(school);

        Clazz clazz = clazzService.getById(teacher.getClazzId());
        teacherDTO.setClazz(clazz);

        List<Course> courseList = courseService.getByTeacherId(teacher.getId());
        teacherDTO.setCourseList(courseList);

        return teacherDTO;
    }

    public Teacher getByUserId(Integer userId){
        Example example = new Example(Teacher.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        return teacherMapper.selectByExample(example).get(0);
    }

    /**
     * 创建教师账号
     * @param dto
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void addTeacher(AddTeacherDTO dto) throws Exception{
        User user = new User();
        user.setName(dto.getTel());
        user.setPwd("test123456");
        user.setRoleId(2);
        user.setCreateTime(new Date());
        userService.saveUser(user);

        Teacher teacher = new Teacher();
        String teacherId = IdUtil.generateId("JS", "500");
        teacher.setName(dto.getTeacherName());
        teacher.setSex(dto.getSex());
        teacher.setAge(dto.getAge());
        teacher.setAddress(dto.getAddress());
        teacher.setNativePlace(dto.getPlaceNative());
        teacher.setSchoolId(dto.getSchoolId());
        teacher.setClazzId(dto.getClazzId());
        teacher.setUserId(user.getId());
        teacher.setTel(dto.getTel());
        teacher.setCreateTime(new Date());
        teacher.setId(teacherId);
        teacherMapper.insert(teacher);

        //创建教师和课程的关系
        TeacherCourse tc;
        Course course;
        for(Integer courseId : dto.getCourseIds()){
            course = courseService.getById(courseId);
            if(null == course){
                throw new RuntimeException(courseId+"该课程不存在");
            }
            tc = new TeacherCourse();
            tc.setCourseId(courseId);
            tc.setTeacherId(teacherId);
            tc.setCreateTime(new Date());
            teacherCourseMapper.insert(tc);
        }
    }

}
