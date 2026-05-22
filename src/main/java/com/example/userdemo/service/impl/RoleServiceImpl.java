package com.example.userdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.userdemo.common.BizException;
import com.example.userdemo.common.Constants;
import com.example.userdemo.entity.Permission;
import com.example.userdemo.entity.Role;
import com.example.userdemo.entity.RolePermission;
import com.example.userdemo.entity.UserRole;
import com.example.userdemo.mapper.PermissionMapper;
import com.example.userdemo.mapper.RoleMapper;
import com.example.userdemo.mapper.RolePermissionMapper;
import com.example.userdemo.mapper.UserRoleMapper;
import com.example.userdemo.service.PermissionService;
import com.example.userdemo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private PermissionService permissionService;

    @Override
    public List<Role> getRolesByUserId(Long userId) {
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, userId);
        List<UserRole> userRoles = userRoleMapper.selectList(wrapper);

        List<Long> roleIds = userRoles.stream()
                .map(UserRole::getRoleId)
                .collect(java.util.stream.Collectors.toList());

        if (roleIds.isEmpty()) {
            return List.of();
        }

        return roleMapper.selectBatchIds(roleIds);
    }

    @Override
    @Transactional
    public void assignRoles(Long userId, List<Long> roleIds) {
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, userId);
        userRoleMapper.delete(wrapper);

        for (Long roleId : roleIds) {
            Role role = roleMapper.selectById(roleId);
            if (role == null) {
                throw new BizException("角色不存在: " + roleId);
            }

            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRole.setCreateTime(LocalDateTime.now());
            userRoleMapper.insert(userRole);
        }
    }
}
