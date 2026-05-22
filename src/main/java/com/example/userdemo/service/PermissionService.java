package com.example.userdemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.userdemo.entity.Permission;

import java.util.List;

public interface PermissionService extends IService<Permission> {

    List<Permission> getPermissionsByUserId(Long userId);

    List<Permission> getPermissionsByRoleId(Long roleId);

    void assignPermissions(Long roleId, List<Long> permissionIds);
}
