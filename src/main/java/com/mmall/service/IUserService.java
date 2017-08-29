package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

public interface IUserService {
    ServerResponse<User> login(String username , String password);

    ServerResponse<User> loginByPhone(String phone , String password);

}

