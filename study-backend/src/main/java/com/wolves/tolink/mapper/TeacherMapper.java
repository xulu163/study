package com.wolves.tolink.mapper;

import com.wolves.tolink.dto.TeaQueryDTO;
import com.wolves.tolink.dto.TeacherViewDTO;
import com.wolves.tolink.entity.Teacher;
import com.wolves.tolink.framework.common.util.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import java.util.List;

@Mapper
@Component
/**
 * @author xulu on 2019/3/11.
 */
public interface TeacherMapper extends MyMapper<Teacher> {

    List<TeacherViewDTO> selectByCondition(TeaQueryDTO dto);

    Integer selectCountByCondition(TeaQueryDTO dto);

}
