package com.example.userdemo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PermissionDTO {

    private Long id;

    @NotBlank(message = "权限名称不能为空")
    private String name;

    @NotBlank(message = "权限代码不能为空")
    private String code;

    private Integer type;

    private String url;

    private Long pid;
}
