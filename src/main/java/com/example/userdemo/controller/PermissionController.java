package com.example.userdemo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.userdemo.common.BizException;
import com.example.userdemo.common.Result;
import com.example.userdemo.dto.PermissionDTO;
import com.example.userdemo.entity.Permission;
import com.example.userdemo.mapper.PermissionMapper;
import com.example.userdemo.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/permission")
@Validated
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PermissionMapper permissionMapper;

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<List<Permission>> listPermissions() {
        return Result.success(permissionService.list());
    }

    @GetMapping("/listByPage")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Page<Permission>> listPermissionsByPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                                           @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Permission> page = new Page<>(pageNum, pageSize);
        return Result.success(permissionService.page(page));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Permission> getPermissionById(@PathVariable Long id) {
        Permission permission = permissionMapper.selectById(id);
        if (permission == null) {
            throw new BizException("权限不存在");
        }
        return Result.success(permission);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> createPermission(@Valid @RequestBody PermissionDTO dto) {
        Permission permission = new Permission();
        permission.setName(dto.getName());
        permission.setCode(dto.getCode());
        permission.setType(dto.getType());
        permission.setUrl(dto.getUrl());
        permission.setPid(dto.getPid() != null ? dto.getPid() : 0L);
        permissionService.save(permission);
        return Result.success();
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> updatePermission(@Valid @RequestBody PermissionDTO dto) {
        if (dto.getId() == null) {
            throw new BizException("权限ID不能为空");
        }

        Permission permission = permissionMapper.selectById(dto.getId());
        if (permission == null) {
            throw new BizException("权限不存在");
        }

        permission.setName(dto.getName());
        permission.setCode(dto.getCode());
        permission.setType(dto.getType());
        permission.setUrl(dto.getUrl());
        if (dto.getPid() != null) {
            permission.setPid(dto.getPid());
        }

        permissionMapper.updateById(permission);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> deletePermission(@PathVariable Long id) {
        permissionService.removeById(id);
        return Result.success();
    }
}
