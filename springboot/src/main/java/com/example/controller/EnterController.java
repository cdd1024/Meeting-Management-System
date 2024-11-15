package com.example.controller;

import com.example.common.Result;
import com.example.entity.Enterprise;
import com.example.service.EnterService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/enterprise")
public class EnterController {
    @Resource
    private EnterService enterService;

    @PutMapping("/update")
    public Result update(@RequestBody Enterprise enterprise){
        enterService.updateById(enterprise);
        return Result.success();
    }
}
