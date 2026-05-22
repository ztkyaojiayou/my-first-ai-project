package com.example.userdemo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.userdemo.common.BizException;
import com.example.userdemo.common.Constants;
import com.example.userdemo.common.Result;
import com.example.userdemo.dto.AssignPermissionDTO;
import com.example.userdemo.dto.RoleDTO;
import com.example.userdemo.entity.Permission;
import com.example.userdemo.entity.Role;
import com.example.userdemo.mapper.RoleMapper;
import com.example.userdemo.service.PermissionService;
import com.example.userdemo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/role")
@Validated
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<List<Role>> listRoles() {
        return Result.success(roleService.list());
    }

    @GetMapping("/listByPage")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Page<Role>> listRolesByPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                               @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Role> page = new Page<>(pageNum, pageSize);
        return Result.success(roleService.page(page));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Role> getRoleById(@PathVariable Long id) {
        Role role = roleMapper.selectById(id);
        if (role == null) {
            throw new BizException("角色不存在");
        }
        return Result.success(role);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> createRole(@Valid @RequestBody RoleDTO dto) {
        Role role = new Role();
        role.setName(dto.getName());
        role.setCode(dto.getCode());
        role.setDescription(dto.getDescription());
        role.setStatus(dto.getStatus() != null ? dto.getStatus() : Constants.STATUS_ENABLE);
        roleService.save(role);
        return Result.success();
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> updateRole(@Valid @RequestBody RoleDTO dto) {
        if (dto.getId() == null) {
            throw new BizException("角色ID不能为空");
        }

        Role role = roleMapper.selectById(dto.getId());
        if (role == null) {
            throw new BizException("角色不存在");
        }

        role.setName(dto.getName());
        role.setCode(dto.getCode());
        role.setDescription(dto.getDescription());
        if (dto.getStatus() != null) {
            role.setStatus(dto.getStatus());
        }

        roleMapper.updateById(role);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> deleteRole(@PathVariable Long id) {
        roleService.removeById(id);
        return Result.success();
    }

    @GetMapping("/{roleId}/permissions")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<List<Permission>> getRolePermissions(@PathVariable Long roleId) {
        return Result.success(permissionService.getPermissionsByRoleId(roleId));
    }

    @PutMapping("/permissions")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> assignPermissions(@RequestBody AssignPermissionDTO dto) {
        permissionService.assignPermissions(dto.getRoleId(), dto.getPermissionIds());
        return Result.success();
    }
}
