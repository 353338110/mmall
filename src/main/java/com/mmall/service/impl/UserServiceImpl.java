package com.mmall.service.impl;

import com.mmall.common.ServerResponse;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iUserService")
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserMapper userMapper;
    /*public UserMapper getUserMapper() {
        return userMapper;
    }
    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }*/

    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if (resultCount ==0 ){
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        // todo 密码登陆MD5

        User user = userMapper.selectLogin(username,password);
        if (user==null){
            return ServerResponse.createByErrorMessage("密码错误");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登陆成功",user);
    }

    @Override
    public ServerResponse<User> loginByPhone(String phone, String password) {
        int resultCount = userMapper.checkPhone(phone);
        if (resultCount == 0){
            return ServerResponse.createByErrorMessage("手机号不存在");
        }
        User user = userMapper.selectLoginByPhone(phone,password);
        if (null == user){
            return ServerResponse.createByErrorMessage("密码错误");
        }
        user.setPassword("");
        return ServerResponse.createBySuccess("登陆成功",user);
    }
}
