package com.example.userdemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.userdemo.entity.Role;

import java.util.List;

public interface RoleService extends IService<Role> {

    List<Role> getRolesByUserId(Long userId);

    void assignRoles(Long userId, List<Long> roleIds);
}
