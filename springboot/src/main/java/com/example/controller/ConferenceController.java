package com.example.controller;

import com.example.common.Result;
import com.example.entity.Conference;
import com.example.service.ConferenceService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/conference")
public class ConferenceController {
    @Resource
    private ConferenceService conferenceService;

    @GetMapping("/selectPage")
    public Result selectPage (@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "5") Integer pageSize,
                              Conference conference){
        PageInfo<Conference> pageInfo = conferenceService.selectPage(pageNum, pageSize,conference);
        return Result.success(pageInfo);
    }


    @GetMapping("/selectById")
    public Result selectPage (){
        List<Conference> conferenceList=conferenceService.selectById();
        return Result.success(conferenceList);
    }
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        conferenceService.deleteById(id);
        return Result.success();
    }
    @PostMapping("/add")
    public Result add(@RequestBody Conference conference){
        conferenceService.add(conference);
        return Result.success();
    }
    @PutMapping("/update")
    public Result update(@RequestBody Conference conference){
        conferenceService.updateById(conference);
        return Result.success();
    }
}
