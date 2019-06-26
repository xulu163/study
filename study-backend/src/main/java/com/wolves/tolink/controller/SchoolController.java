package com.wolves.tolink.controller;

import com.wolves.tolink.framework.common.response.ResponseData;
import com.wolves.tolink.service.SchoolService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/school")
/**
 * @author xulu
 */
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    @ApiOperation(value="获取所有学校", notes="获取所有学校")
    @GetMapping(value = "/list")
    public ResponseData listClazz(){
        ResponseData result = new ResponseData();
        result.setCode(0);
        result.setMsg("success");
        result.setData(schoolService.getAll());
        return result;
    }

}
