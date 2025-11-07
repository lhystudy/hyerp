package com.hyerp.dao;

import com.hyerp.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int insert(User record);
    int updateByPrimaryKey(User record);
    int deleteByPrimaryKey(Long id);
    User selectByPrimaryKey(Long id);
    User selectByUsername(String username);
}

