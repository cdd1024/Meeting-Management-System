package com.example.entity;

import lombok.Data;

@Data
public class Administrator extends Account {
    private String phone;
    private String email;
    private String sex;
    private String creat;
    private String avatar;

}
