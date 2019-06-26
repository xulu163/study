package com.wolves.tolink.service;

import com.wolves.tolink.dto.ResourceDTO;
import com.wolves.tolink.mapper.ResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xulu
 */
@Service
public class ResourceService {

    @Autowired
    ResourceMapper resourceMapper;

    public List<ResourceDTO> getByUserId(Integer userId){
        return resourceMapper.getByUserId(userId);
    }

}