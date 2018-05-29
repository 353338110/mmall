package com.mmall.controller;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Mixed;
import com.mmall.pojo.User;
import com.mmall.service.IFileService;
import com.mmall.service.IMixedService;
import com.mmall.service.IRedisService;
import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
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
    @Autowired
    private IRedisService iRedisService;
    @RequestMapping("upload.do")
    @ResponseBody
    public ServerResponse uploadMixed(String uid,@RequestParam(value = "mixed",required = false) String mixed, @RequestParam(value = "upload_file",required = false)MultipartFile[] files) {
       /* User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }*/
        if (!iRedisService.exists(uid)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆");
        }
        iRedisService.expire(uid,Const.CACHE_TIME);
        Mixed acc  = new Mixed();
        ObjectMapper mapper = new ObjectMapper().setVisibility(JsonMethod.FIELD,
                JsonAutoDetect.Visibility.ANY);
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
                false);

        try {
            acc = mapper.readValue(mixed, Mixed.class);
            acc.setId(StringUtils.getPrimarykey());
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> fileUrls = new ArrayList<>();
        if (null==files || files.length==0){
            return iMixedService.upLoadMixed(acc);
        }

        String path = Const.IMAGE_PATH;
        for (MultipartFile file:files) {
            String targetFileName  = iFileService._upload(file,path);
            logger.info(targetFileName);
            fileUrls.add(Const.IMAGE_RETURN+targetFileName);
        }
        if (null== StringUtils.mixedReplace(acc.getMixedtext(),fileUrls)){
            return ServerResponse.createByErrorMessage("嵌入的参数不对应");
        }
        acc.setMixedtext(StringUtils.mixedReplace(acc.getMixedtext(),fileUrls));

        return iMixedService.upLoadMixed(acc);
    }


    @RequestMapping(value = "download.do",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<byte[]> download(HttpServletRequest request)throws Exception {
        //下载文件路径
        String filename = "patch.jar";
        String path = request.getServletContext().getRealPath("/hotfix/");
        File file = new File(path + File.separator + filename);

        HttpHeaders headers = new HttpHeaders();
        //下载显示的文件名，解决中文名称乱码问题
        String downloadFielName = new String(filename.getBytes("UTF-8"),"iso-8859-1");
        //通知浏览器以attachment（下载方式）打开图片
        headers.setContentDispositionFormData("attachment", downloadFielName);
        //application/octet-stream ： 二进制流数据（最常见的文件下载）。
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.CREATED);
    }
}
