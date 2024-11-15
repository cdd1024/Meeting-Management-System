package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Role;
import com.example.entity.Account;
import com.example.entity.Enterprise;
import com.example.exception.CustomException;
import com.example.mapper.AdminMapper;
import com.example.mapper.EnterMapper;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service

public class EnterService {
    @Resource
    private EnterMapper enterMapper;
    public Account login(Account account){
        Account dbEnter = enterMapper.selectByUsername(account.getUsername());
        if(dbEnter==null){
            //没有用户
            throw new CustomException("账号不存在");
        }
        //比较密码
        if(!account.getPassword().equals(dbEnter.getPassword())){
            throw new CustomException("密码错误");
        }
        return dbEnter;
    }

    public void register(Account account) {
        Enterprise enterprise = new Enterprise();
        enterprise.setUsername(account.getUsername());
        enterprise.setPassword(account.getPassword());
        enterprise.setCompany(account.getCompany());
        enterprise.setPhone(account.getPhone());
        this.add(enterprise);
        System.out.println("注册的测试");

    }
    private void add(Enterprise enterprise) {
        Enterprise dbEnterprise2 = enterMapper.selectByCompany(enterprise.getCompany());
        if(dbEnterprise2!=null){
            throw new CustomException("公司已注册");
        }
        Enterprise dbEnterprise1 = enterMapper.selectByUsername(enterprise.getUsername());
        if(dbEnterprise1!=null){
            throw new CustomException("账号已存在");
        }

        if(ObjectUtil.isEmpty(enterprise.getName())){
            enterprise.setName(enterprise.getUsername());
        }
        enterprise.setRole(Role.ENTERPRISE.name());
        enterMapper.insert(enterprise);
    }

    public void updateById(Enterprise enterprise) {
        enterMapper.updateById(enterprise);
    }
}
