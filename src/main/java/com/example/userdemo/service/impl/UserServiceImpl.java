package com.example.userdemo.service.impl;

import com.example.userdemo.common.BizException;
import com.example.userdemo.common.Constants;
import com.example.userdemo.entity.User;
import com.example.userdemo.service.UserService;
import com.example.userdemo.util.InMemoryStorage;
import com.example.userdemo.util.JwtUtil;
import com.example.userdemo.util.PasswordEncoder;
import com.example.userdemo.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private InMemoryStorage inMemoryStorage;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public User getUserByUsername(String username) {
        return inMemoryStorage.getUserByUsername(username);
    }

    @Override
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
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setDeleted(0);

        inMemoryStorage.addUser(user);
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

        String token = jwtUtil.generateToken(username);

        return token;
    }

    @Override
    public void logout(String token) {
        if (StringUtils.hasText(token)) {
            String username = jwtUtil.extractUsername(token);

            Long expire = jwtUtil.getExpiration();
            redisUtil.set("token:blacklist:" + token, "1", expire, TimeUnit.MILLISECONDS);
        }
    }
}