package com.example.hit_networking_base.domain.dto.response;

import com.example.hit_networking_base.domain.entity.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

public class UserResponseDTO {

    int userId;
    String username;
    User.Role role;
    String fullName;
    String email;
    String phone;
    String avatarUrl;
    LocalDateTime createdAt;
    LocalDateTime deletedAt;
    List<CV> cvs;
    List<Comment> comments;
    List<Reaction> reactions;
    List<Event> createdEvents;
    List<JobPost> createdJobPosts;
}
