package com.example.mapper;

import com.example.entity.Account;
import com.example.entity.Administrator;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface AdminMapper {
    @Select("select * from administrator where username=#{username}")
    Administrator selectByUsername(String username);

    @Update("update administrator set username=#{username},password=#{password},name=#{name},phone=#{phone},email=#{email}," +
            "sex=#{sex},avatar=#{avatar} where id=#{id}")
    void updateById(Administrator administrator);
}
