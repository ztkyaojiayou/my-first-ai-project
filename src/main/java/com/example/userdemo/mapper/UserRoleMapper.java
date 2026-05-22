package com.example.userdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.userdemo.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {
}
