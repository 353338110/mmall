package com.mmall.controller;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Mixed;
import com.mmall.pojo.User;
import com.mmall.service.IFileService;
import com.mmall.service.IMixedService;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/mixed/")
public class MixedController {
    private Logger logger = LoggerFactory.getLogger(MixedController.class);
    @Autowired
    private IMixedService iMixedService;
    @Autowired
    private IFileService iFileService;
    @RequestMapping("upload.do")
    @ResponseBody
    public ServerResponse uploadMixed(HttpSession session,@RequestParam(value = "mixed",required = false) String mixed, @RequestParam(value = "upload_file",required = false)MultipartFile[] files) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        if (null==user.getId()){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"找不到用户Id");
        }
        Mixed acc  = new Mixed();
        ObjectMapper mapper = new ObjectMapper().setVisibility(JsonMethod.FIELD,
                JsonAutoDetect.Visibility.ANY);
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
                false);

        try {
            acc = mapper.readValue(mixed, Mixed.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> fileUrls = new ArrayList<>();
        if (null==files || files.length==0){
            return iMixedService.upLoadMixed(acc);
        }

        String path = Const.IMAGE_PATH;
        for (MultipartFile file:files) {
            String targetFileName  = iFileService.upload(file,path);
            logger.info(targetFileName);
            fileUrls.add(Const.IMAGE_RETURN+targetFileName);
        }
        if (null== StringUtils.mixedReplace(acc.getMixedtext(),fileUrls)){
            return ServerResponse.createByErrorMessage("嵌入的参数不对应");
        }
        acc.setMixedtext(StringUtils.mixedReplace(acc.getMixedtext(),fileUrls));
        acc.setId(StringUtils.getPrimarykey());
        return iMixedService.upLoadMixed(acc);
    }
}
