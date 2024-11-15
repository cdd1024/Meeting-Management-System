package com.example.entity;

import lombok.Data;

@Data
public class Conference {
    private Integer id;
    private String name;
    private String statu;
    private String descr;
    private String company;
    private String avatar;


    private String example;
    private String examplea;
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")



}
