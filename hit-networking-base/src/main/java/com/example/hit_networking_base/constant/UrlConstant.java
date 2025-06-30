package com.example.hit_networking_base.constant;

public class UrlConstant {
    public static class Authorization{
        public static final String PREFIX = "/auth";
        public static final String LOGIN = PREFIX + "/login";
        public static final String REST_PASSWORD = PREFIX + "/forgot-password";
    }

    public static class User{
        public static final String PREFIX = "/users";
        public static final String CHANGE_PASSWORK = PREFIX + "/change-password";
    }

    public static class Event{
        public static final String PREFIX = "/admin/event";
        public static final String CREATE_EVENT = PREFIX;
        public static final String GET_EVENT = PREFIX;
        public static final String GET_EVENTS = PREFIX + "s";
    }
}
