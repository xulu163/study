package com.wolves.tolink.controller;

import com.wolves.tolink.dto.ResourceDTO;
import com.wolves.tolink.entity.User;
import com.wolves.tolink.framework.common.response.ResponseData;
import com.wolves.tolink.service.ResourceService;
import com.wolves.tolink.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author xulu
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    UserService userService;
    @Autowired
    ResourceService resourceService;

    @ApiOperation(value="获取用户菜单", notes="获取用户菜单")
    @GetMapping(value = "/list")
    public ResponseData listMenu(@RequestHeader("Authorization") String token){
        ResponseData result = new ResponseData();
        User user;
        try{
            user = userService.selectByToken(token);
        } catch (Exception e){
            result.setCode(-1);
            result.setMsg(e.getMessage());
            return result;
        }

        List<ResourceDTO> resourceList = resourceService.getByUserId(user.getId());
        result.setCode(0);
        result.setMsg("success");
        result.setData(resourceList);
        return result;
    }
}
