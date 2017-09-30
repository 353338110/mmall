package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.dao.MixedMapper;
import com.mmall.dao.MoodLogMapper;
import com.mmall.pojo.MoodLog;
import com.mmall.pojo.MoodLogPic;
import com.mmall.service.IMoodLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("iMoodLogService")
public class MoodLogServiceImpl implements IMoodLogService{
    private Logger logger = LoggerFactory.getLogger(MixedServiceImpl.class);

    @Autowired
    private MoodLogMapper moodLogMapper;


    @Override
    public ServerResponse<String> upLoadMoodLog(MoodLog moodLog) {

        int insert = moodLogMapper.insert(moodLog);
        if (insert==0){
            return ServerResponse.createByErrorMessage("MoodLog文字上传数据库失败");
        }
        return ServerResponse.createBySuccess("MoodLog文字上传数据库成功!");
    }

    public ServerResponse<PageInfo> getMoodLog(int currentPage,int pageSize){
      return null;
    }
}
