package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.MixedMapper;
import com.mmall.pojo.Mixed;
import com.mmall.pojo.User;
import com.mmall.service.IMixedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.RandomPrimarykey;

import javax.servlet.http.HttpSession;

@Service("iMixedService")
public class MixedServiceImpl implements IMixedService{
    private Logger logger = LoggerFactory.getLogger(MixedServiceImpl.class);

    @Autowired
    private  MixedMapper mixedMapper;

    @Override
    public ServerResponse<String> upLoadMixed(HttpSession session,String mixedtext){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (null==user){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (null==user.getId()){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"没有找到用户id");
        }
        Mixed mixed = new Mixed(RandomPrimarykey.getPrimarykey(),user.getId(),null,null,mixedtext);
        int insert = mixedMapper.insert(mixed);
        if (insert==0){
            return ServerResponse.createByErrorMessage("上传失败,请重新上传.");
        }
        return ServerResponse.createBySuccessMessage("上传成功!");
    }

}
