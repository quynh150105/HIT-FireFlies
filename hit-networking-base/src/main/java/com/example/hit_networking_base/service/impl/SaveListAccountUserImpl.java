package com.example.hit_networking_base.service.impl;

import com.example.hit_networking_base.domain.entity.User;
import com.example.hit_networking_base.repository.UserRepository;
import com.example.hit_networking_base.service.SaveListAccountUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.hit_networking_base.service.ExcelUploadService;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SaveListAccountUserImpl implements SaveListAccountUser {
    private final UserRepository repository;
    private final ExcelUploadService uploadService;
    @Override
    public void saveListAccUsersToDatabase(MultipartFile file) {
        List<User> users = null;
        try {
            users = uploadService.getCustomerDataFromExcel(file.getInputStream()).getUsers();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        repository.saveAll(users);
    }


}
