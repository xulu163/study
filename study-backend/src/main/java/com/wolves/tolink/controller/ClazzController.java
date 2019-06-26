package com.wolves.tolink.controller;

import com.wolves.tolink.framework.common.response.ResponseData;
import com.wolves.tolink.service.ClazzService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clazz")
/**
 * @author xulu
 */
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    @ApiOperation(value="获取班级列表", notes="获取班级列表")
    @GetMapping(value = "/list")
    public ResponseData listClazz(){
        ResponseData result = new ResponseData();
        result.setCode(0);
        result.setMsg("success");
        result.setData(clazzService.getAll());
        return result;
    }

}
