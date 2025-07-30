package com.example.hit_networking_base.service.impl;

import com.cloudinary.Cloudinary;
import com.example.hit_networking_base.constant.ErrorMessage;
import com.example.hit_networking_base.constant.TargetType;
import com.example.hit_networking_base.domain.entity.Image;
import com.example.hit_networking_base.exception.BadRequestException;
import com.example.hit_networking_base.exception.NotFoundException;
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
    private final CheckListServiceImpl checkListService;

    @Override
    public List<String> uploadImage(MultipartFile[] files, TargetType targetType, long targetId)
            throws IOException {
        List<String> urls = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file.getSize() > 5 * 1024 * 1024) {
                throw new BadRequestException(ErrorMessage.Image.ERR_OVER_SIZE_IMAGE + " over 5Mb");
            }


//            Đã tạo được nhưng mất phí => không chạy
//            if(!checkListService.checkImage(file)){
//                throw new BadRequestException(ErrorMessage.Image.ERR_SENSITIVE_CONTENT);
//            }

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

    @Override
    public List<String> getUrlImage(long targetId, TargetType targetType) {
        List<Image> images = imageRepository.findByTargetTypeAndTargetId(targetType, targetId);
        List<String> urls = new ArrayList<>();
        for(Image image : images)
            urls.add(image.getImageUrl());
        return urls;
    }
}
