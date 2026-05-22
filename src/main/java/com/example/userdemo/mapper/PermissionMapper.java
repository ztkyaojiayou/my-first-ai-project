package com.example.userdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.userdemo.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
}
