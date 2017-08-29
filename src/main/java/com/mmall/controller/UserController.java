package com.mmall.controller;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService iUserService;
    public IUserService getIUserService() {
        return iUserService;
    }
    @Autowired
    public void setIUserService(IUserService iUserService) {
        this.iUserService = iUserService;
    }
    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    //用用户名登陆
    public ServerResponse login(String username, String password, HttpSession session){
        ServerResponse<User> response = iUserService.login(username,password);
        if (response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    @RequestMapping(value = "login2.do",method = RequestMethod.POST)
    @ResponseBody
    //用手机号登陆
    public ServerResponse loginByPhone(String phone, String password, HttpSession session){
        ServerResponse<User> response = iUserService.loginByPhone(phone,password);
        if (response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    @RequestMapping(value = "logout.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return (ServerResponse<String>)ServerResponse.createBySuccess();
    }

}
