package com.example.hit_networking_base.constant;

import org.springframework.security.access.method.P;

public class UrlConstant {
    public static class Admin{
        public static final String PREFIX = "admin";
        public static final String CREATE = PREFIX + "/create";
        public static final String UPDATE = PREFIX + "/update";
        public static final String IMPORT = PREFIX + "/import";
    }

    public static class User{
        public static final String PREFIX= "user";
        public static final String UPDATE = PREFIX + "/update";s
        public static final String SHOW = PREFIX + "/show";
    }
}
