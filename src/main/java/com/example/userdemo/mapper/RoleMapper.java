package com.example.userdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.userdemo.entity.Role;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
}
