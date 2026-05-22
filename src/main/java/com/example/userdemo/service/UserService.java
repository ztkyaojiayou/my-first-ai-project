package com.example.userdemo.service;

import com.example.userdemo.entity.User;

public interface UserService {

    User getUserByUsername(String username);

    void register(String username, String password, String nickname, String email, String phone);

    String login(String username, String password);

    void logout(String token);
}