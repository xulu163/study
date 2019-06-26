package com.wolves.tolink.mapper;

import com.wolves.tolink.dto.ResourceDTO;
import com.wolves.tolink.entity.Resource;
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
public interface ResourceMapper extends MyMapper<Resource> {

    List<ResourceDTO> getByUserId(@Param("userId") Integer userId);

}
