package com.example.hit_networking_base.service;

import com.example.hit_networking_base.domain.dto.request.RequestCvDTO;
import com.example.hit_networking_base.domain.dto.response.CvResponseDTO;

import java.util.List;

public interface CvService {
    CvResponseDTO createCv(RequestCvDTO dto);

    List<CvResponseDTO> getCVsByPostId(Long postId);
    List<CvResponseDTO> getCVsByUserId(Long userId);
}
