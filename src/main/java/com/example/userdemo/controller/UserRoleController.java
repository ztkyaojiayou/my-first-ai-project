package com.example.userdemo.controller;

import com.example.userdemo.common.Result;
import com.example.userdemo.dto.AssignRoleDTO;
import com.example.userdemo.entity.Role;
import com.example.userdemo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Validated
public class UserRoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/{userId}/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<List<Role>> getUserRoles(@PathVariable Long userId) {
        return Result.success(roleService.getRolesByUserId(userId));
    }

    @PutMapping("/{userId}/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> assignUserRoles(@PathVariable Long userId, @RequestBody AssignRoleDTO dto) {
        dto.setUserId(userId);
        roleService.assignRoles(userId, dto.getRoleIds());
        return Result.success();
    }
}
