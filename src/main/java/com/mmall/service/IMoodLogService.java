package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.MoodLog;

public interface IMoodLogService {
    ServerResponse<String> upLoadMoodLog(MoodLog moodLog);
    ServerResponse<PageInfo> getMoodLog(int currentPage,int pageSize);
}
