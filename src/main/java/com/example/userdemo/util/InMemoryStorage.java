package com.example.userdemo.util;

import com.example.userdemo.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 内存存储工具类
 * 功能：在内存中存储用户数据（不依赖数据库）
 */
@Component
public class InMemoryStorage {

    // 用户存储：username -> User
    private final Map<String, User> userMap = new HashMap<>();

    // ID 生成器
    private final AtomicLong idGenerator = new AtomicLong(3);

    // 初始化默认用户
    public InMemoryStorage() {
        // 初始化管理员
        User admin = new User();
        admin.setId(1L);
        admin.setUsername("admin");
        admin.setPassword(PasswordEncoder.encode("123456"));
        admin.setNickname("管理员");
        admin.setEmail("admin@example.com");
        admin.setPhone("13800138000");
        admin.setStatus(1);
        admin.setCreateTime(LocalDateTime.now());
        admin.setUpdateTime(LocalDateTime.now());
        admin.setDeleted(0);
        userMap.put("admin", admin);

        // 初始化测试用户
        User test = new User();
        test.setId(2L);
        test.setUsername("test");
        test.setPassword(PasswordEncoder.encode("123456"));
        test.setNickname("测试用户");
        test.setEmail("test@example.com");
        test.setPhone("13800138001");
        test.setStatus(1);
        test.setCreateTime(LocalDateTime.now());
        test.setUpdateTime(LocalDateTime.now());
        test.setDeleted(0);
        userMap.put("test", test);
    }

    /**
     * 根据用户名查询用户
     */
    public User getUserByUsername(String username) {
        return userMap.get(username);
    }

    /**
     * 根据ID查询用户
     */
    public User getUserById(Long id) {
        for (User user : userMap.values()) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    /**
     * 查询所有用户
     */
    public List<User> getAllUsers() {
        return new ArrayList<>(userMap.values());
    }

    /**
     * 添加用户
     */
    public void addUser(User user) {
        if (user.getId() == null) {
            user.setId(idGenerator.getAndIncrement());
        }
        userMap.put(user.getUsername(), user);
    }

    /**
     * 更新用户
     */
    public void updateUser(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMap.put(user.getUsername(), user);
    }

    /**
     * 删除用户
     */
    public boolean deleteUser(Long id) {
        for (Map.Entry<String, User> entry : userMap.entrySet()) {
            if (entry.getValue().getId().equals(id)) {
                userMap.remove(entry.getKey());
                return true;
            }
        }
        return false;
    }
}
