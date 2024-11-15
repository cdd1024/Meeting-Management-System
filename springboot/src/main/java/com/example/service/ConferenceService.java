package com.example.service;

import com.example.entity.Conference;

import com.example.exception.CustomException;
import com.example.mapper.ConferenceMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


import java.util.List;
@Service

public class ConferenceService {

    @Resource
    private ConferenceMapper conferenceMapper;
    public PageInfo<Conference> selectPage(Integer pageNum, Integer pageSize, Conference conference) {
        PageHelper.startPage(pageNum,pageSize);
        List<Conference> conferenceList=conferenceMapper.selectAll(conference);
        return PageInfo.of(conferenceList);
    }
    public void deleteById(Integer id) {
        conferenceMapper.deleteById(id);
    }

    public void add(Conference conference) {
        Conference dbConference = conferenceMapper.selectByName(conference.getName());
        if(dbConference!=null){
            throw new CustomException("会议名已存在");
        }
        conferenceMapper.insert(conference);
    }
    public void updateById(Conference conference) {
        conferenceMapper.updateById(conference);
    }
    public List<Conference> selectById() {
       return conferenceMapper.selectById();
    }
}
