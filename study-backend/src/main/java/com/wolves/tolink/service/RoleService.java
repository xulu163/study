package com.wolves.tolink.service;

import com.wolves.tolink.entity.Role;
import com.wolves.tolink.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xulu
 */
@Service
public class RoleService {

    @Autowired
    RoleMapper roleMapper;

    public Role getById(Integer id){
        return roleMapper.selectByPrimaryKey(id);
    }

}
