package com.fwtai.dao;

import com.fwtai.dataobject.UserPasswordDO;

public interface UserPasswordDOMapper{

    int deleteByPrimaryKey(Integer id);

    int insert(UserPasswordDO record);

    int insertSelective(UserPasswordDO record);

    UserPasswordDO selectByPrimaryKey(Integer id);

    UserPasswordDO selectPasswordByUserId(Integer userId);

    int updateByPrimaryKeySelective(UserPasswordDO record);

    int updateByPrimaryKey(UserPasswordDO record);
}