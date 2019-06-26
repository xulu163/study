package com.wolves.tolink.service;

import com.wolves.tolink.entity.School;
import com.wolves.tolink.mapper.SchoolMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author xulu
 */
@Service
public class SchoolService {

    @Autowired
    SchoolMapper schoolMapper;

    public List<School> getAll(){
        return schoolMapper.selectAll();
    }

    public School getById(Integer id){
        return schoolMapper.selectByPrimaryKey(id);
    }


}
