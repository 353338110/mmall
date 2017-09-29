package com.mmall.controller;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.UIDUser;
import com.mmall.pojo.User;
import com.mmall.service.IRedisService;
import com.mmall.service.IUserService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import util.StringUtils;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IRedisService iRedisService;

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
        String uid = StringUtils.getUUID();
        if (response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
            iRedisService.put(uid,response.getData(),Const.CACHE_TIME);
            UIDUser uidUser = new UIDUser(uid,response.getData());
            ServerResponse<UIDUser> uidResponse = ServerResponse.createBySuccess(uidUser);
            return uidResponse;
        }
        return ServerResponse.createByErrorMessage("登陆失败");
    }

    @RequestMapping(value = "login2.do",method = RequestMethod.POST)
    @ResponseBody
    //用手机号登陆
    public ServerResponse loginByPhone(String phone, String password, HttpSession session){
        ServerResponse<User> response = iUserService.loginByPhone(phone,password);
        String uid = StringUtils.getUUID();
        if (response.isSuccess()){
            iRedisService.put(uid,response.getData(),Const.CACHE_TIME);
            UIDUser uidUser = new UIDUser(uid,response.getData());
            ServerResponse<UIDUser> uidResponse = ServerResponse.createBySuccess(uidUser);
            return uidResponse;
        }
        return ServerResponse.createByErrorMessage("登陆失败");
    }

    @RequestMapping(value = "logout.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> logout(String uid){
        iRedisService.delete(uid);
        return (ServerResponse<String>)ServerResponse.createBySuccess();
    }

}
