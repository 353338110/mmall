package com.mmall.controller;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IFileService;
import com.mmall.service.IMixedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/mixed/")
public class MixedController {
    private Logger logger = LoggerFactory.getLogger(MixedController.class);
    @Autowired
    private IMixedService iMixedService;
    @RequestMapping("upload.do")
    @ResponseBody
    public ServerResponse uploadMixed(HttpSession session, @RequestParam(value = "mixedtext")String mixedtext) {
        //todo 可以设置用户权限还有必须登录用户才能上传
        /*User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }*/
        int resultCode = iMixedService.upLoadMixed(session,mixedtext).getStatus();

        return null;
    }
}
