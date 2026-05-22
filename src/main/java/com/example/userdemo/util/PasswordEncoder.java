package com.example.userdemo.util;

import cn.hutool.crypto.SecureUtil;

public class PasswordEncoder {

    public static String encode(String rawPassword) {
        return SecureUtil.md5(rawPassword);
    }

    public static Boolean matches(String rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}
