package com.example.userdemo.dto;

import lombok.Data;

import java.util.List;

@Data
public class AssignRoleDTO {

    private Long userId;

    private List<Long> roleIds;
}
