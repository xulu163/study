package com.wolves.tolink.mapper;

import com.wolves.tolink.dto.StuQueryDTO;
import com.wolves.tolink.dto.StudentViewDTO;
import com.wolves.tolink.entity.Student;
import com.wolves.tolink.framework.common.util.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import java.util.List;

@Mapper
@Component
/**
 * @author xulu on 2019/3/11.
 */
public interface StudentMapper extends MyMapper<Student> {

    List<StudentViewDTO> selectByCondition(StuQueryDTO dto);

    Integer selectCountByCondition(StuQueryDTO dto);

}
