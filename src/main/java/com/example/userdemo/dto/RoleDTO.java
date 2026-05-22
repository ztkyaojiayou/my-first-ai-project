package com.example.userdemo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RoleDTO {

    private Long id;

    @NotBlank(message = "角色名称不能为空")
    private String name;

    @NotBlank(message = "角色代码不能为空")
    private String code;

    private String description;

    private Integer status;
}
