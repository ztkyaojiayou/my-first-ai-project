package com.example.userdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.userdemo.common.BizException;
import com.example.userdemo.common.Constants;
import com.example.userdemo.entity.Role;
import com.example.userdemo.entity.User;
import com.example.userdemo.entity.UserRole;
import com.example.userdemo.mapper.UserMapper;
import com.example.userdemo.mapper.UserRoleMapper;
import com.example.userdemo.service.PermissionService;
import com.example.userdemo.service.RoleService;
import com.example.userdemo.service.UserService;
import com.example.userdemo.util.JwtUtil;
import com.example.userdemo.util.PasswordEncoder;
import com.example.userdemo.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public User getUserByUsername(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        return userMapper.selectOne(wrapper);
    }

    @Override
    @Transactional
    public void register(String username, String password, String nickname, String email, String phone) {
        User existUser = getUserByUsername(username);
        if (existUser != null) {
            throw new BizException("用户名已存在");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(PasswordEncoder.encode(password));
        user.setNickname(StringUtils.hasText(nickname) ? nickname : username);
        user.setEmail(email);
        user.setPhone(phone);
        user.setStatus(Constants.STATUS_ENABLE);
        userMapper.insert(user);
    }

    @Override
    public String login(String username, String password) {
        User user = getUserByUsername(username);
        if (user == null) {
            throw new BizException("用户不存在");
        }

        if (!PasswordEncoder.matches(password, user.getPassword())) {
            throw new BizException("密码错误");
        }

        if (user.getStatus().equals(Constants.STATUS_DISABLE)) {
            throw new BizException("用户已被禁用");
        }

        List<Role> roles = roleService.getRolesByUserId(user.getId());
        List<String> permissions = roles.stream()
                .flatMap(role -> permissionService.getPermissionsByRoleId(role.getId()).stream())
                .map(p -> p.getCode())
                .distinct()
                .collect(Collectors.toList());

        if (permissions.isEmpty()) {
            permissions.add("ROLE_USER");
        }

        String token = jwtUtil.generateToken(username);

        redisUtil.set("permission:" + username, permissions, Constants.PERMISSION_CACHE_TTL, TimeUnit.MINUTES);

        return token;
    }

    @Override
    public void logout(String token) {
        if (StringUtils.hasText(token)) {
            String username = jwtUtil.extractUsername(token);
            redisUtil.delete("permission:" + username);

            Long expire = jwtUtil.getExpiration();
            redisUtil.set("token:blacklist:" + token, "1", expire, TimeUnit.MILLISECONDS);
        }
    }
}
