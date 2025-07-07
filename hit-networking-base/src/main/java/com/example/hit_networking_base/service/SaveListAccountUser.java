package com.example.hit_networking_base.service;

import com.example.hit_networking_base.domain.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SaveListAccountUser {
    String saveListAccUsersToDatabase(MultipartFile file);
    List<User> getAllUser();
}
