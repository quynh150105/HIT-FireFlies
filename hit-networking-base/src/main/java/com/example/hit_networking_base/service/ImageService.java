package com.example.hit_networking_base.service;

import com.cloudinary.Cloudinary;
import com.example.hit_networking_base.constant.TargetType;
import com.example.hit_networking_base.repository.ImageRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    List<String> uploadImage(MultipartFile[] files, TargetType targetType, Integer targetId) throws IOException;

}
