package com.mmall.dao;

import com.mmall.pojo.FileBean;

public interface FileBeanMapper {
    int deleteByPrimaryKey(String id);

    int insert(FileBean record);

    int insertSelective(FileBean record);

    FileBean selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FileBean record);

    int updateByPrimaryKey(FileBean record);
}