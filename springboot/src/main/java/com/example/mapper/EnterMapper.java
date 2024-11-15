package com.example.mapper;

import com.example.entity.Account;
import com.example.entity.Administrator;
import com.example.entity.Enterprise;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface EnterMapper {
    @Select("select * from enterprise where username=#{username}")
    Enterprise selectByUsername(String username);
    @Insert("insert into enterprise(username,password,name,role,phone,email,sex,`create`,avatar,company) " +
            "values(#{username},#{password},#{name},#{role},#{phone},#{email},#{sex},#{create},#{avatar},#{company})")
    void insert(Enterprise enterprise);

    @Update("update enterprise set username=#{username},password=#{password},name=#{name},phone=#{phone},email=#{email}," +
            "sex=#{sex},avatar=#{avatar} where id=#{id}")
    void updateById(Enterprise enterprise);

    @Select("select * from enterprise where company=#{company}")
    Enterprise selectByCompany(String company);
}
