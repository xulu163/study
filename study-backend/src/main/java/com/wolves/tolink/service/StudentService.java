package com.wolves.tolink.service;

import com.wolves.tolink.dto.*;
import com.wolves.tolink.entity.*;
import com.wolves.tolink.dto.*;
import com.wolves.tolink.entity.*;
import com.wolves.tolink.framework.common.response.QueryResult;
import com.wolves.tolink.mapper.StudentCourseMapper;
import com.wolves.tolink.mapper.StudentMapper;
import com.wolves.tolink.util.IdUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author xulu
 */
@Service
public class StudentService {

    @Autowired
    StudentMapper studentMapper;
    @Autowired
    SchoolService schoolService;
    @Autowired
    ClazzService clazzService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    CourseService courseService;
    @Autowired
    StudentCourseMapper studentCourseMapper;

    /**
     * 条件查询
     * @param dto
     * @return
     */
    public QueryResult<StudentViewDTO> getByCondition(StuQueryDTO dto){
        //teacherId为空表示不区分谁的学生，获取所有
        List<StudentViewDTO> list = studentMapper.selectByCondition(dto);
        Integer count = studentMapper.selectCountByCondition(dto);

        QueryResult<StudentViewDTO> result = new QueryResult<StudentViewDTO>();
        result.setRows(list);
        result.setTotal(count);
        return result;
    }

    /**
     * 获取学生的详细信息
     * @param id
     * @return StudentDTO
     */
    public StudentDTO getById(String id) throws RuntimeException{
        Student stu = studentMapper.selectByPrimaryKey(id);
        if(null == stu){
            throw new RuntimeException("id有误，该学生信息不存在");
        }
        StudentDTO studentDTO = new StudentDTO();
        BeanUtils.copyProperties(stu, studentDTO);

        School school = schoolService.getById(stu.getSchoolId());
        studentDTO.setSchool(school);

        Clazz clazz = clazzService.getById(stu.getClazzId());
        studentDTO.setClazz(clazz);

        Teacher teacher = teacherService.getById(stu.getTeacherId());
        studentDTO.setTeacher(teacher);

        List<CourseDTO> courseList = courseService.getByStudentId(stu.getId());
        studentDTO.setCourses(courseList);
        return studentDTO;
    }

    public void deleteById(String id) throws RuntimeException{
        Student stu = studentMapper.selectByPrimaryKey(id);
        if(null == stu){
            throw new RuntimeException("id有误，该学生信息不存在");
        }
        studentMapper.delete(stu);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void add(AddStudentDTO dto, Teacher teacher) throws RuntimeException{
        StudentCourse studentCourse;
        List<AddStudentCourseDTO> list = dto.getCourseList();
        Double totalScore = 0D;
        for(AddStudentCourseDTO addStudentCourseDTO : list){
            totalScore = totalScore + addStudentCourseDTO.getScore();
        }
        Integer totalCourse = list.size();
        Double avgScore = totalScore / totalCourse;
        Student student = new Student();
        BeanUtils.copyProperties(dto, student);
        student.setCreateTime(new Date());
        String stuId = IdUtil.generateId("ST", "001");
        student.setId(stuId);
        student.setTeacherId(teacher.getId());
        student.setTotalScore(totalScore);
        student.setTotalCourse(totalCourse);
        student.setAvgScore(avgScore);

        studentMapper.insert(student);

        Course course;
        for(AddStudentCourseDTO addStudentCourseDTO : list){
            Integer courseId = addStudentCourseDTO.getCourseId();
            course = courseService.getById(courseId);
            if(null == course){
                throw new RuntimeException(courseId+"该课程不存在");
            }
            studentCourse = new StudentCourse();
            studentCourse.setCourseId(courseId);
            studentCourse.setScore(addStudentCourseDTO.getScore());
            studentCourse.setStuId(stuId);
            studentCourse.setCreateTime(new Date());
            try{
                studentCourseMapper.insert(studentCourse);
            } catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException("课程不能重复");
            }
        }
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void updateStu(UpdateStudentDTO updateStu){

        //更新课程
        List<UpdateStudentCourseDTO> courseList = updateStu.getCourseList();
        StudentCourse studentCourse;
        Double totalScore = 0D;
        Course course;
        for(UpdateStudentCourseDTO dto : courseList){
            Integer courseId = dto.getCourseId();
            course = courseService.getById(courseId);
            if(null == course){
                throw new RuntimeException(courseId+"该课程不存在");
            }
            studentCourse = new StudentCourse();
            studentCourse.setScore(dto.getScore());
            studentCourse.setStuId(updateStu.getId());
            studentCourse.setCourseId(dto.getCourseId());
            try{
                if(null != dto.getId()){
                    studentCourse.setId(dto.getId());
                    studentCourse.setUpdateTime(new Date());
                    studentCourseMapper.updateByPrimaryKeySelective(studentCourse);
                } else {
                    studentCourse.setCreateTime(new Date());
                    studentCourseMapper.insert(studentCourse);
                }
            } catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException("课程不能重复");
            }

            totalScore = totalScore + dto.getScore();
        }
        Integer totalCourse = courseList.size();
        Double avgScore = totalScore / totalCourse;
        Student student = new Student();
        BeanUtils.copyProperties(updateStu, student);
        student.setUpdateTime(new Date());
        student.setTotalCourse(totalCourse);
        student.setAvgScore(avgScore);
        student.setTotalScore(totalScore);
        studentMapper.updateByPrimaryKeySelective(student);
    }

    public Integer getTotalByTeacher(String teacherId){
        Example example = new Example(Student.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("teacherId", teacherId);

        return studentMapper.selectCountByExample(example);
    }

}
