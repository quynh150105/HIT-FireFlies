package com.example.hit_networking_base.service.impl;

import com.cloudinary.Cloudinary;
import com.example.hit_networking_base.constant.TargetType;
import com.example.hit_networking_base.domain.entity.Image;
import com.example.hit_networking_base.repository.ImageRepository;
import com.example.hit_networking_base.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final Cloudinary cloudinary;
    private final ImageRepository imageRepository;

    @Override
    public List<String> uploadImage(MultipartFile[] files, TargetType targetType, Integer targetId)
            throws IOException {
        List<String> urls = new ArrayList<>();

        for (MultipartFile file : files) {
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), Map.of());
            String url = (String) uploadResult.get("secure_url");
            urls.add(url);

            Image image = new Image();
            image.setTargetType(targetType);
            image.setTargetId(targetId);
            image.setImageUrl(url);
            image.setUploadedAt(LocalDateTime.now());

            imageRepository.save(image);
        }
        return urls;
    }
}
