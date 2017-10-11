package com.mmall.dao;

import com.mmall.pojo.MoodLog;
import com.mmall.pojo.MoodLogUrl;

import java.util.ArrayList;
import java.util.List;

public interface MoodLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(MoodLog record);

    int insertSelective(MoodLog record);

    MoodLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MoodLog record);

    int updateByPrimaryKey(MoodLog record);

    List<MoodLogUrl> queryByDate();
}