package com.example.userdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.userdemo.entity.RolePermission;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
}
