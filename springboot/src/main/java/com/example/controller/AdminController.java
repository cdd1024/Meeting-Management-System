package com.example.controller;

import com.example.common.Result;
import com.example.entity.Administrator;
import com.example.service.AdminService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/administrator")
public class AdminController {
    @Resource
    AdminService adminService;
    @PutMapping("/update")
    public Result update(@RequestBody Administrator administrator){
        adminService.updateById(administrator);
        return Result.success();
    }
}
