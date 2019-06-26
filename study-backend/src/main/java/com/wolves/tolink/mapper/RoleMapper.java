package com.wolves.tolink.mapper;

import com.wolves.tolink.entity.Role;
import com.wolves.tolink.framework.common.util.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
/**
 * @author xulu on 2019/3/11.
 */
public interface RoleMapper extends MyMapper<Role> {

}
