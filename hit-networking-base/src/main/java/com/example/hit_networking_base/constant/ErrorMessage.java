package com.example.hit_networking_base.constant;

public class ErrorMessage {

    public static class User{
        public static final String ERR_NOT_FOUND_USER_NAME = "exception.user.not.found.username";
        public static final String ERR_NOT_FOUND_USER_ID = "exception.user.not.found.userId";
        public static final String ERR_INVALID_PASSWORD = "exception.user.invalid.password";
        public static final String ERR_SAME_PASSWORD = "exception.user.same.password";
        public static final String ERR_ALREADY_EXISTS_USER_NAME = "exception.user.already.exists.username";
        public static final String ERR_ALREADY_EXISTS_EMAIL = "exception.user.already.exists.email";
        public static final String ERR_NOT_FOUND_EMAIL = "exception.user.not.found.email";
        public static final String ERR_NOT_AUTHENTICATED = "exception.user.not.authenticated";
        public static final String ERR_NOT_ENOUGH_RIGHTS = "exception.user.not.enough.right";
    }

    public static class Auth{
        public static final String ERR_SEND_EMAIL = "exception.auth.send.failed.email";
    }

    public static class Image{
        public static final String ERR_UPLOAD = "exception.image.upload.failed";
        public static final String ERR_NOT_FOUND_IMAGE = "exception.image.not.found";
    }

    public static class Reaction{
        public static final String ERR_NOT_FOUND_REACTION = "exception.reaction.not.found";
    }

    public static class Comment{
        public static final String ERR_NOT_FOUND_COMMENT = "exception.comment.not.found.id";
        public static final String ERR_NOT_ENOUGH_RIGHTS = "exception.comment.not.enough.right";
    }

    public static class ImportFileExcel{
        public static final String ERR_WRONG_FORMAT ="exception.import.wrong.format.file";
        public static final String ERR_WRONG_READ ="exception.import.wrong.read.file";
    }

    public static class Event{
        public static final String ERR_NOT_FOUND_EVENT = "exception.event.not.found.evnetId";
    }

    public static class Job{
        public static final String ERR_NOT_FOUND_JOB_ID ="exception.job.not.found.postId";
        public static final String ERR_NOT_ENOUGH_RIGHTS = "exception.job.not.enough.right";
    }

    public static final class Email{
        public static final String ERR_SEND_RESET_PASS = "exception.email.can't.send.email.reset.password";
        public static final String ERR_SEND_CREATE_USER = "exception.email.can't.send.email.create.user";
        public static final String ERR_SEND_CREATE_POST = "exception.email.can't.send.email.create.post";
    }

}
