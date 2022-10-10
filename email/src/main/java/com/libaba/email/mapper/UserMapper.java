package com.libaba.email.mapper;

import com.libaba.email.model.User;
import org.mybatis.spring.annotation.MapperScan;


public interface UserMapper {
    /*
      注册，插入数据
     */
    void insertUser(User user);

    /*
     根据邮箱查询
     */
    User queryByEmail(String email);


    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}