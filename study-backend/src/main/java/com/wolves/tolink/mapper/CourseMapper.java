package com.wolves.tolink.mapper;

import com.wolves.tolink.dto.CourseDTO;
import com.wolves.tolink.dto.CourseQueryDTO;
import com.wolves.tolink.dto.CourseResultDTO;
import com.wolves.tolink.entity.Course;
import com.wolves.tolink.framework.common.util.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
/**
 * @author xulu on 2019/3/11.
 */
public interface CourseMapper extends MyMapper<Course> {

    List<Course> selectByTeacherId(@Param("teacherId") String teacherId);

    List<CourseDTO> selectByStudentId(@Param("stuId") String stuId);

    List<CourseResultDTO> selectByCondition(CourseQueryDTO dto);

    Integer selectCountByCondition(CourseQueryDTO dto);

}
