package com.example.hit_networking_base.constant;

import org.springframework.security.core.parameters.P;

import java.awt.event.PaintEvent;

public class UrlConstant {
    public static class Admin{
        public static final String PREFIX = "admin";
        public static final String CREATE = PREFIX + "/create";
        public static final String UPDATE = PREFIX + "/update";
        public static final String IMPORT = PREFIX + "/import";
        public static final String GET_ALL = PREFIX + "/select-all";
        public static final String RESTORE = PREFIX + "/restore";
        public static final String DELETE = PREFIX + "/delete";
        public static final String GET_DETAIL = PREFIX + "/get-detail";
    }

    public static class Authorization{
        public static final String PREFIX = "/auth";
        public static final String LOGIN = PREFIX + "/login";
        public static final String REST_PASSWORD = PREFIX + "/forgot-password";
        public static final String HOME = PREFIX + "/home";
        public static final String REFRESH = PREFIX + "/refresh";
    }

    public static class User{
        public static final String PREFIX = "/users";
        public static final String CHANGE_PASSWORD = PREFIX + "/change-password";
        public static final String USER_INFO = PREFIX + "/me";
        public static final String USER_UPDATE_INFO = PREFIX + "/me";
        public static final String UPDATE = PREFIX;
        public static final String RESET_PASSWORD = PREFIX + "/set-password";
    }

    public static class Event{
        public static final String PREFIX = "/admin/event";
        public static final String CREATE_EVENT = PREFIX;
        public static final String GET_EVENT = PREFIX;
        public static final String GET_EVENTS = PREFIX + 's';
        public static final String UPDATE_EVENT = PREFIX + "/{eventId}";
        public static final String DELETE = PREFIX;

    }

    public static class JobPost{
        public static final String PREFIT = "/user/job";
        public static final String CREATE_JOB_POST = PREFIT;
        public static final String GET_JOB_POST_PAGE = PREFIT + "s";
        public static final String GET_JOB_DETAIL = PREFIT;
        public static final String UPDATE_JOB = PREFIT;
        public static final String DELETE = PREFIT;
    }

    public static class Comment{
        public static final String PREFIX_USER = "/user/comment";
        public static final String PREFIX_ADMIN = "/admin/comment";
        public static final String CREATE = PREFIX_USER;
        public static final String UPDATE = PREFIX_USER;
        public static final String DELETE = PREFIX_USER;
        public static final String GET_DETAIL = PREFIX_ADMIN;
    }

    public static class Reaction{
        public static final String  PREFIX = "/reaction";

        public static final String CREATE_OR_UPDATE = PREFIX + "/react";

        public static final String DELETE = PREFIX + "/delete";

        public static final String GET_ALL = PREFIX + "/get-all";


    }

    public static class Cv{
        public static final String PREFIX ="/cv";

        public static final String UP_CV = PREFIX + "/up";

        public static final String GET_BY_USERID = PREFIX + "/get-by-user";

        public static final String GET_BY_POST = PREFIX + "/get-by-post";
    }
}
