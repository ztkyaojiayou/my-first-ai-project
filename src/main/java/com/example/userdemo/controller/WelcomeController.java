package com.example.userdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "<!DOCTYPE html>\n" +
               "<html lang=\"zh-CN\">\n" +
               "<head>\n" +
               "    <meta charset=\"UTF-8\">\n" +
               "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
               "    <title>用户管理系统</title>\n" +
               "    <style>\n" +
               "        body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; display: flex; justify-content: center; align-items: center; margin: 0; }\n" +
               "        .container { background: white; border-radius: 16px; box-shadow: 0 20px 60px rgba(0,0,0,0.3); padding: 40px 60px; text-align: center; max-width: 500px; }\n" +
               "        h1 { color: #333; font-size: 2.5rem; margin-bottom: 10px; }\n" +
               "        .subtitle { color: #666; font-size: 1.1rem; margin-bottom: 30px; }\n" +
               "        .api-list { text-align: left; background: #f8f9fa; border-radius: 8px; padding: 20px; }\n" +
               "        .api-item { display: flex; margin: 12px 0; align-items: center; }\n" +
               "        .method { padding: 4px 12px; border-radius: 4px; font-size: 0.8rem; font-weight: bold; margin-right: 12px; }\n" +
               "        .method.post { background: #52c41a; color: white; }\n" +
               "        .method.get { background: #1890ff; color: white; }\n" +
               "        .method.put { background: #faad14; color: white; }\n" +
               "        .method.delete { background: #f5222d; color: white; }\n" +
               "        .endpoint { color: #333; font-family: 'Monaco', 'Menlo', monospace; }\n" +
               "        .desc { color: #888; font-size: 0.9rem; margin-left: 8px; }\n" +
               "        .footer { margin-top: 30px; color: #999; font-size: 0.85rem; }\n" +
               "        .logo { font-size: 4rem; margin-bottom: 10px; }\n" +
               "    </style>\n" +
               "</head>\n" +
               "<body>\n" +
               "    <div class=\"container\">\n" +
               "        <div class=\"logo\">👤</div>\n" +
               "        <h1>用户管理系统</h1>\n" +
               "        <p class=\"subtitle\">User Management System API</p>\n" +
               "        \n" +
               "        <div class=\"api-list\">\n" +
               "            <div class=\"api-item\"><span class=\"method post\">POST</span><span class=\"endpoint\">/api/user/register</span><span class=\"desc\">用户注册</span></div>\n" +
               "            <div class=\"api-item\"><span class=\"method post\">POST</span><span class=\"endpoint\">/api/user/login</span><span class=\"desc\">用户登录</span></div>\n" +
               "            <div class=\"api-item\"><span class=\"method post\">POST</span><span class=\"endpoint\">/api/user/logout</span><span class=\"desc\">用户登出</span></div>\n" +
               "            <div class=\"api-item\"><span class=\"method get\">GET</span><span class=\"endpoint\">/api/user/info</span><span class=\"desc\">获取用户信息</span></div>\n" +
               "            <div class=\"api-item\"><span class=\"method get\">GET</span><span class=\"endpoint\">/api/user/list</span><span class=\"desc\">用户列表</span></div>\n" +
               "            <div class=\"api-item\"><span class=\"method put\">PUT</span><span class=\"endpoint\">/api/user</span><span class=\"desc\">更新用户</span></div>\n" +
               "            <div class=\"api-item\"><span class=\"method delete\">DELETE</span><span class=\"endpoint\">/api/user/{id}</span><span class=\"desc\">删除用户</span></div>\n" +
               "            <div class=\"api-item\"><span class=\"method get\">GET</span><span class=\"endpoint\">/api/role/list</span><span class=\"desc\">角色列表</span></div>\n" +
               "            <div class=\"api-item\"><span class=\"method get\">GET</span><span class=\"endpoint\">/api/permission/list</span><span class=\"desc\">权限列表</span></div>\n" +
               "        </div>\n" +
               "        \n" +
               "        <div class=\"footer\">\n" +
               "            <p>默认账号: admin / 123456 | test / 123456</p>\n" +
               "            <p>技术栈: Spring Boot 2.7.2 + MyBatis-Plus 3.5.1 + MySQL + Redis</p>\n" +
               "        </div>\n" +
               "    </div>\n" +
               "</body>\n" +
               "</html>";
    }
}
