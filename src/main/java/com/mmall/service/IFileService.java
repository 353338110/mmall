package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
    String upload(MultipartFile file,String path,String userId,String relationId,int type);
    String _upload(MultipartFile file,String path);
}
