package com.wolves.tolink.service;

import com.wolves.tolink.entity.Clazz;
import com.wolves.tolink.mapper.ClazzMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author xulu
 */
@Service
public class ClazzService {

    @Autowired
    ClazzMapper clazzMapper;

    public List<Clazz> getAll(){
        return clazzMapper.selectAll();
    }

    public Clazz getById(Integer id){
        return clazzMapper.selectByPrimaryKey(id);
    }

}
