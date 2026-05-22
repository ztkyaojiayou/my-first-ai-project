package com.example.userdemo.dto;

import lombok.Data;

import java.util.List;

@Data
public class AssignPermissionDTO {

    private Long roleId;

    private List<Long> permissionIds;
}
