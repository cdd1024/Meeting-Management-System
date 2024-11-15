package com.example.entity;

import lombok.Data;

@Data
public class Enterprise extends Account{
    private String phone;
    private String email;
    private String sex;
    private String create;
    private String avatar;
    private String company;

}
