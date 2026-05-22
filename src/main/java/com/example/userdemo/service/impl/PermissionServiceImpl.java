package com.example.userdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.userdemo.common.BizException;
import com.example.userdemo.entity.Permission;
import com.example.userdemo.entity.Role;
import com.example.userdemo.entity.RolePermission;
import com.example.userdemo.mapper.PermissionMapper;
import com.example.userdemo.mapper.RoleMapper;
import com.example.userdemo.mapper.RolePermissionMapper;
import com.example.userdemo.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public List<Permission> getPermissionsByUserId(Long userId) {
        return Collections.emptyList();
    }

    @Override
    public List<Permission> getPermissionsByRoleId(Long roleId) {
        LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RolePermission::getRoleId, roleId);
        List<RolePermission> rolePermissions = rolePermissionMapper.selectList(wrapper);

        List<Long> permissionIds = rolePermissions.stream()
                .map(RolePermission::getPermissionId)
                .collect(java.util.stream.Collectors.toList());

        if (permissionIds.isEmpty()) {
            return Collections.emptyList();
        }

        return permissionMapper.selectBatchIds(permissionIds);
    }

    @Override
    @Transactional
    public void assignPermissions(Long roleId, List<Long> permissionIds) {
        Role role = roleMapper.selectById(roleId);
        if (role == null) {
            throw new BizException("角色不存在: " + roleId);
        }

        LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RolePermission::getRoleId, roleId);
        rolePermissionMapper.delete(wrapper);

        for (Long permissionId : permissionIds) {
            Permission permission = permissionMapper.selectById(permissionId);
            if (permission == null) {
                throw new BizException("权限不存在: " + permissionId);
            }

            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            rolePermission.setCreateTime(LocalDateTime.now());
            rolePermissionMapper.insert(rolePermission);
        }
    }
}
