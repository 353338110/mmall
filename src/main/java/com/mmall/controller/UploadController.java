package com.mmall.controller;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IFileService;
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
@RequestMapping("/rich/")
public class UploadController {
    private Logger logger = LoggerFactory.getLogger(UploadController.class);
    @Autowired
    private IFileService iFileService;
    @RequestMapping("upload.do")
    @ResponseBody
    public ServerResponse upload(HttpSession session, @RequestParam(value = "upload_file",required = false)MultipartFile[] files, HttpServletRequest request){
        //todo 可以设置用户权限还有必须登录用户才能上传
        /*User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }*/

        //String path = request.getSession().getServletContext().getRealPath("upload");
        List<String> fileUrls = new ArrayList<>();
        if (null==files || files.length==0){
            return ServerResponse.createByErrorMessage("文件为空");
        }
        String path = Const.IMAGE_PATH;
        for (MultipartFile file:files) {
            String targetFileName  = iFileService.upload(file,path);
            logger.info(targetFileName);
            fileUrls.add(Const.IMAGE_RETURN+targetFileName);
        }
        return ServerResponse.createBySuccess("上传成功",fileUrls);
    }
}
