package com.wolves.tolink.service;

import com.wolves.tolink.dto.CourseDTO;
import com.wolves.tolink.dto.CourseQueryDTO;
import com.wolves.tolink.dto.CourseResultDTO;
import com.wolves.tolink.entity.Course;
import com.wolves.tolink.framework.common.response.QueryResult;
import com.wolves.tolink.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author xulu
 */
@Service
public class CourseService {

    @Autowired
    CourseMapper courseMapper;

    public List<Course> getAll(){
        return courseMapper.selectAll();
    }

    /**
     * 查询学生所学课程列表
     * @param studentId
     * @return
     */
    public List<CourseDTO> getByStudentId(String studentId){
        return courseMapper.selectByStudentId(studentId);
    }

    /**
     * 查询教师所教课程列表
     * @param teacherId
     * @return
     */
    public List<Course> getByTeacherId(String teacherId){
        return courseMapper.selectByTeacherId(teacherId);
    }

    public Course getById(Integer id){
        return courseMapper.selectByPrimaryKey(id);
    }

    /**
     * 学生课程条件搜索结果
     * @param courseQueryDTO
     * @return
     */
    public QueryResult<CourseResultDTO> getByCondition(CourseQueryDTO courseQueryDTO){

        List<CourseResultDTO> list = courseMapper.selectByCondition(courseQueryDTO);
        Integer count = courseMapper.selectCountByCondition(courseQueryDTO);

        QueryResult<CourseResultDTO> result = new QueryResult<CourseResultDTO>();
        result.setRows(list);
        result.setTotal(count);
        return result;
    }

}
