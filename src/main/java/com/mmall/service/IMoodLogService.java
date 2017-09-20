package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.MoodLog;

public interface IMoodLogService {
    ServerResponse<String> upLoadMoodLog(MoodLog moodLog);
}
