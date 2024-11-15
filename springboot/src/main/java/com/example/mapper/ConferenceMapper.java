package com.example.mapper;

import com.example.entity.Conference;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ConferenceMapper {
    @Select("select * from conference where name=#{name}")
    Conference selectByName(String name);



    @Select("select * from conference where name like concat('%',#{name},'%') and company like concat('%',#{company},'%')" +
            "and convert(example,DATETIME) like concat('%',#{example},'%') order by id desc")
    List<Conference> selectAll(Conference conference);

    @Delete("delete from conference where id=#{id}")
    void deleteById(Integer id);

    @Insert("insert into conference (name,statu,descr,company,avatar,example,examplea) values(" +
            "#{name},#{statu},#{descr},#{company},#{avatar},#{example},#{examplea})")
    void insert(Conference conference);

    @Update("update conference set name=#{name},statu=#{statu},company=#{company},avatar=#{avatar},example=#{example}," +
            "examplea=#{examplea},descr=#{descr} where id=#{id}")
    void updateById(Conference conference);

    @Select("select * from conference")
    List<Conference> selectById();
}
