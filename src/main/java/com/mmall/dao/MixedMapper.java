package com.mmall.dao;

import com.mmall.pojo.Mixed;

public interface MixedMapper {
    int deleteByPrimaryKey(String id);

    int insert(Mixed record);

    int insertSelective(Mixed record);

    Mixed selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Mixed record);

    int updateByPrimaryKeyWithBLOBs(Mixed record);

    int updateByPrimaryKey(Mixed record);
}