package com.example.hit_networking_base.constant;

public class UrlConstant {
    public static class Authorization{
        public static final String PREFIX = "auth";
        public static final String LOGIN = PREFIX + "/login";
        public static final String REST_PASSWORD = PREFIX + "/forgot-password";
    }

    public static class User{
        public static final String PREFIX = "users";
        public static final String CHANGE_PASSWORK = PREFIX + "/change-password";
    }
}
