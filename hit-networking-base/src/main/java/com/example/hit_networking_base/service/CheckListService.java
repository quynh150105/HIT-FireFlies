package com.example.hit_networking_base.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface CheckListService {
    boolean checkImage(MultipartFile file);
    void checkListText(Map<String, String> fields);
}
