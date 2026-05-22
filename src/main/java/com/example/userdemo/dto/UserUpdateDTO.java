package com.example.userdemo.dto;

import lombok.Data;

@Data
public class UserUpdateDTO {

    private Long id;

    private String nickname;

    private String email;

    private String phone;

    private String avatar;

    private Integer status;
}
