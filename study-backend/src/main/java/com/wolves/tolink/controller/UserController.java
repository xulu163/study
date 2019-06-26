package com.wolves.tolink.controller;

import com.wolves.tolink.dto.LoginDTO;
import com.wolves.tolink.dto.UpdatePwdDTO;
import com.wolves.tolink.entity.User;
import com.wolves.tolink.framework.common.response.ResponseData;
import com.wolves.tolink.service.UserService;
import com.wolves.tolink.util.Md5Util;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.util.StringUtil;

@RestController
@RequestMapping("/user")
/**
 * @author xulu
 */
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value="登陆接口", notes="登陆接口")
    @PostMapping(value = "/login")
    public ResponseData<String> login(@RequestBody LoginDTO loginDTO){

        return userService.login(loginDTO);
    }

    @ApiOperation(value="登出接口", notes="登出接口")
    @PostMapping(value = "/logout")
    public ResponseData logout(@RequestHeader("Authorization") String token){
        ResponseData result = new ResponseData();
        User user;
        try{
            user = userService.selectByToken(token);
        } catch (Exception e){
            result.setCode(-1);
            result.setMsg(e.getMessage());
            return result;
        }
        user.setToken("");
        userService.updateUser(user);
        result.setCode(0);
        result.setMsg("success");
        return result;
    }

    @ApiOperation(value="修改密码", notes="修改密码")
    @PostMapping(value = "/updatePwd")
    public ResponseData updatePwd(@RequestHeader("Authorization") String token,
                                  @RequestBody UpdatePwdDTO dto){
        ResponseData result = new ResponseData();
        User user;
        try{
            user = userService.selectByToken(token);
        } catch (Exception e){
            result.setCode(-1);
            result.setMsg(e.getMessage());
            return result;
        }
        if(!dto.getOldPwd().equals(user.getPwd())){
            result.setCode(-1);
            result.setMsg("原始密码错误");
            return result;
        }
        if(StringUtil.isEmpty(dto.getNewPwd())){
            result.setCode(-1);
            result.setMsg("新密码不能为空");
            return result;
        }
        if(!dto.getNewPwd().equals(dto.getSurePwd())){
            result.setCode(-1);
            result.setMsg("两次新密码输入不一致");
            return result;
        }
        String sign = Md5Util.md5(dto.getOldPwd()+dto.getNewPwd()+dto.getTimeStamp());
        if(!sign.equals(dto.getSign())){
            result.setCode(-1);
            result.setMsg("验签失败");
            return result;
        }
        user.setPwd(dto.getNewPwd());
        userService.updateUser(user);
        result.setCode(0);
        result.setMsg("success");
        return result;
    }
}
