package com.mmall.controller;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.FileBean;
import com.mmall.pojo.MoodLog;
import com.mmall.pojo.User;
import com.mmall.service.IFileService;
import com.mmall.service.IMoodLogService;
import com.mmall.service.IRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/moodlog/")
public class MoodLogController {
    private Logger logger = LoggerFactory.getLogger(MoodLogController.class);
    @Autowired
    private IFileService iFileService;
    @Autowired
    private IMoodLogService iMoodLogService;
    @Autowired
    private IRedisService iRedisService;
    @RequestMapping("mood.do")
    @ResponseBody
    public ServerResponse upload(@RequestParam String uid, @RequestParam String title, @RequestParam String content, @RequestParam MultipartFile[] files, HttpServletRequest request){

        if (!iRedisService.exists(uid)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        iRedisService.expire(uid,Const.CACHE_TIME);
        User user =iRedisService.get(uid,User.class);
        /*MoodLog moodLog  = new MoodLog();
        ObjectMapper mapper = new ObjectMapper().setVisibility(JsonMethod.FIELD,
                JsonAutoDetect.Visibility.ANY);
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
                false);

        try {
            moodLog = mapper.readValue(moodLogs, MoodLog.class);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        MoodLog moodLog = new MoodLog();
        moodLog.setTitle(title);
        moodLog.setContent(content);
        String moodLogId = StringUtils.getPrimarykey();
        moodLog.setId(moodLogId);
        moodLog.setType(1);
        moodLog.setUserid(user.getId());
        int result = iMoodLogService.upLoadMoodLog(moodLog).getStatus();
        if (result==1){
            return ServerResponse.createByErrorMessage("MoodLog数据库插入失败");
        }

        //String path = request.getSession().getServletContext().getRealPath("upload");
        String fileUrls = "";
        if (null==files || files.length==0){
            return ServerResponse.createBySuccessMessage("上传成功");
        }


        String path = Const.IMAGE_PATH;
        try {
            for (MultipartFile file:files) {
                String targetFileName  = iFileService.upload(file,path,user.getId(),moodLogId,1);
                logger.info(targetFileName);
                if ("".equals(fileUrls)){
                    fileUrls = fileUrls+Const.IMAGE_RETURN+targetFileName;
                }else {
                    fileUrls = fileUrls + ","+(Const.IMAGE_RETURN+targetFileName);
                }
                //fileUrls.add(Const.IMAGE_RETURN+targetFileName);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }

        FileBean fileBean = new FileBean();
        fileBean.setId(StringUtils.getPrimarykey());
        fileBean.setRelationId(moodLog.getId());
        fileBean.setUserId(moodLog.getUserid());
        fileBean.setType(moodLog.getType());
        fileBean.setUrl(fileUrls);
        int status = iFileService.uploadBean(fileBean).getStatus();
        if (status != 0){
            ServerResponse.createByErrorMessage("上传失败");
        }
        return ServerResponse.createBySuccess("上传成功");
    }
    @RequestMapping("query_mood.do")
    @ResponseBody
    public ServerResponse queryMoodLog(@RequestParam String uid,int currentPage,int pageSize){
        if (!iRedisService.exists(uid)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        iRedisService.expire(uid,Const.CACHE_TIME);
        return iMoodLogService.getMoodLog(currentPage,pageSize);
    }
}
