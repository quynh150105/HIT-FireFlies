package com.example.hit_networking_base.constant;

public class ErrorMessage {
    public static class User{
        public static final String ERR_NOT_FOUND_USER_NAME = "exception.user.not.found.username";
        public static final String ERR_INVALID_PASSWORD = "exception.user.invalid.password";
        public static final String ERR_SAME_PASSWORD = "exception.user.same.password";
        public static final String ERR_NOT_FOUND_EMAIL = "exception.user.not.found.email";
    }

    public static class Auth{
        public static final String ERR_SEND_EMAIL = "exception.auth.send.failed.email";
    }

    public static class Image{
        public static final String ERR_UPLOAD = "exception.image.upload.failed";
    }
}
