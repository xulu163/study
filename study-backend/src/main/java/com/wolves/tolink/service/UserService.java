package com.wolves.tolink.service;

import com.wolves.tolink.dto.LoginDTO;
import com.wolves.tolink.dto.UserDTO;
import com.wolves.tolink.entity.Role;
import com.wolves.tolink.entity.User;
import com.wolves.tolink.framework.common.response.ResponseData;
import com.wolves.tolink.framework.common.util.UUidUtil;
import com.wolves.tolink.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

@Service
/**
 * @author xulu on 2019/3/11.
 */
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleService roleService;

    public void saveUser(User entity){
        userMapper.insert(entity);
    }

    public void updateUser(User user){
        userMapper.updateByPrimaryKeySelective(user);
    }

    public ResponseData<String> login(LoginDTO loginDTO){
        ResponseData<String> result = new ResponseData();
        String name = loginDTO.getName();
        String pwd = loginDTO.getPwd();

        if(StringUtil.isEmpty(name)){
            result.setCode(-1);
            result.setMsg("用户名不能为空");
            return result;
        }
        if(StringUtil.isEmpty(pwd)){
            result.setCode(-1);
            result.setMsg("密码不能为空");
            return result;
        }
        User user = new User();
        user.setName(name);
        user.setPwd(pwd);
        user = userMapper.selectOne(user);
        if(null == user){
            result.setCode(-1);
            result.setMsg("密码错误");
            return result;
        }
        String token = UUidUtil.generateUuid();
        user.setToken(token);
        userMapper.updateByPrimaryKeySelective(user);
        result.setCode(0);
        result.setMsg("success");
        result.setData(token);
        return result;
    }

    public UserDTO selectByToken(String token) throws Exception{

        if(StringUtil.isEmpty(token)){
            throw new Exception("token不能为空");
        }
        User user = new User();
        user.setToken(token);
        user = userMapper.selectOne(user);
        if(null == user){
            throw new Exception("无效的token");
        }

        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        Role role = roleService.getById(user.getRoleId());
        userDTO.setRole(role);
        return userDTO;
    }
}
