package com.mmall.dao;

import com.mmall.pojo.MoodLog;

import java.util.List;

public interface MoodLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(MoodLog record);

    int insertSelective(MoodLog record);

    MoodLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MoodLog record);

    int updateByPrimaryKey(MoodLog record);

    List<MoodLog> quaryByDate();
}