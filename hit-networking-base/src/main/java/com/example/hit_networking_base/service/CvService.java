package com.example.hit_networking_base.service;

import com.example.hit_networking_base.domain.dto.request.CVCreateRequestDTO;
import com.example.hit_networking_base.domain.dto.request.CVUpdateRequestDTO;
import com.example.hit_networking_base.domain.dto.response.CvResponseDTO;
import com.example.hit_networking_base.domain.dto.response.MyCVResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface CvService {
    CvResponseDTO createCv(CVCreateRequestDTO dto);
    Page<CvResponseDTO> getCVsByPostId(Long postId, int page, int size);
    Page<MyCVResponseDTO> getMyCVS(int page, int size);
    String deleteCV(Long postId);
    MyCVResponseDTO updateCV(Long id, CVUpdateRequestDTO cvUpdateRequestDTO);
    void downloadCV(Long postId, HttpServletResponse response);

}
