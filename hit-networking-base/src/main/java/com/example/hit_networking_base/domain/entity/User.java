package com.example.hit_networking_base.domain.entity;


import com.example.hit_networking_base.constant.Gender;
import com.example.hit_networking_base.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


import java.util.List;
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(length = 50, nullable = false, name= "username", unique = true)
    private String username;

    @Column(name = "password_hash", length = 255, nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private LocalDate dob;

    @Column(name = "full_name", length = 100)
    private String fullName;

    @Column(length = 100)
    private String email;

    @Column(length = 20)
    private String phone;

    @Column(name = "avatar_url", length = 255)
    private String avatarUrl;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    @OneToMany(mappedBy = "user")
    private List<CV> cvs;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @OneToMany(mappedBy = "user")
    private List<Reaction> reactions;

    @OneToMany(mappedBy = "creator")
    private List<Event> createdEvents;

    @OneToMany(mappedBy = "creator")
    private List<JobPost> createdJobPosts;

}