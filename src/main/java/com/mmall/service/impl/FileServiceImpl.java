package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.dao.FileBeanMapper;
import com.mmall.pojo.FileBean;
import com.mmall.service.IFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service("iFileService")
public class FileServiceImpl implements IFileService{
    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Autowired
    private FileBeanMapper fileBeanMapper;
    @Override
    public String upload(MultipartFile file, String path,String userId,String relationId,int type) {
        String fileName = file.getOriginalFilename();
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;
        logger.info("开始上传文件,上传文件的文件名:{},上传的路径:{},新文件名:{}",fileName,path,uploadFileName);
        File fileDir = new File(path);
        if (!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdir();
        }
        File targetFile = new File(path,uploadFileName);
        try {
            file.transferTo(targetFile);
            //文件已经上传成功了
            //FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            //已经上传到ftp服务器上
            //targetFile.delete();
            /*FileBean fileBean = new FileBean();
            fileBean.setId(StringUtils.getPrimarykey());
            fileBean.setRelationId(relationId);
            fileBean.setUserId(userId);
            fileBean.setType(type);
            fileBean.setUrl(Const.IMAGE_RETURN+targetFile.getName());
            int result = fileBeanMapper.insert(fileBean);
            if (result==0){
                return null;
            }*/
        } catch (IOException e) {
            logger.error("上传文件异常",e);
            return null;
        }

        return targetFile.getName();

    }

    @Override
    public String _upload(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;
        logger.info("开始上传文件,上传文件的文件名:{},上传的路径:{},新文件名:{}",fileName,path,uploadFileName);
        File fileDir = new File(path);
        if (!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdir();
        }

        File targetFile = new File(path,uploadFileName);

        try {
            file.transferTo(targetFile);
            //文件已经上传成功了
            //FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            //已经上传到ftp服务器上
            //targetFile.delete();
        } catch (IOException e) {
            logger.error("上传文件异常",e);
            return null;
        }
        return targetFile.getName();
    }

    @Override
    public ServerResponse<String> uploadBean(FileBean fileBean) {
        if (null==fileBean){
            return ServerResponse.createByErrorMessage("fileBean为空");
        }
        int result = fileBeanMapper.insert(fileBean);
        if (result==0){
            return ServerResponse.createByErrorMessage("fileBean插入失败");
        }

        return ServerResponse.createBySuccess("fileBean插入成功");
    }

    public String uploadBean(MultipartFile file, String path,String userId,String relationId,int type) {
        String fileName = file.getOriginalFilename();
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;
        logger.info("开始上传文件,上传文件的文件名:{},上传的路径:{},新文件名:{}",fileName,path,uploadFileName);
        File fileDir = new File(path);
        if (!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdir();
        }

        File targetFile = new File(path,uploadFileName);
        FileBean fileBean = new FileBean();
        fileBean.setId(StringUtils.getPrimarykey());
        fileBean.setRelationId(relationId);
        fileBean.setUserId(userId);
        fileBean.setType(type);
        fileBean.setUrl(Const.IMAGE_RETURN+targetFile.getName());
        try {
            file.transferTo(targetFile);
            //文件已经上传成功了
            //FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            //已经上传到ftp服务器上
            //targetFile.delete();

            int result = fileBeanMapper.insert(fileBean);
            if (result==0){
                return null;
            }
        } catch (IOException e) {
            logger.error("上传文件异常",e);
            return null;
        }

        return targetFile.getName();

    }
}
