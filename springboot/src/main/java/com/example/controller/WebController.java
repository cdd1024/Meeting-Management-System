package com.example.controller;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Result;
import com.example.common.Role;
import com.example.entity.Account;
import com.example.entity.Enterprise;
import com.example.service.AdminService;
import com.example.service.EnterService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class WebController {

    @Resource
    private AdminService adminService;

    @Resource
    private EnterService enterService;

    /**
     * 默认请求接口
     */
    @GetMapping("/")
    public Result hello() {
        return Result.success();
    }


    @PostMapping("/login")
    public Result login(@RequestBody Account account){
        Account dbAccount;
        System.out.println(account.getRole());
        System.out.println(account.getName());
        System.out.println(account.getUsername());
        System.out.println(account.getPassword());

        if(Role.ADMINISTRATOR.name().equals(account.getRole())){//管理员登录
            dbAccount=adminService.login(account);
        }else if(Role.ENTERPRISE.name().equals(account.getRole())){//学生登录
            dbAccount=enterService.login(account);

        }else{
            return Result.error("角色错误");
        }
        return Result.success(dbAccount);
    }

    @PostMapping("/register")
    public Result register(@RequestBody Account account){

        if(ObjectUtil.isEmpty(account.getUsername())||ObjectUtil.isEmpty(account.getPassword())){
            return Result.error("账号和密码必填");
        }
        enterService.register(account);
        return Result.success();
    }



}
