package com.example.hit_networking_base.service;

import com.example.hit_networking_base.domain.dto.response.HomeResponseDTO;
import org.springframework.data.domain.Page;

public interface HomeService {
    Page<HomeResponseDTO> getALLEventAndJobPost(int page, int size);
}
