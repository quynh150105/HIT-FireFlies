package com.example.hit_networking_base.service;

import com.example.hit_networking_base.domain.dto.request.EventRequest;
import com.example.hit_networking_base.domain.dto.response.EventResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface EventService {
    EventResponseDTO createEvent(EventRequest eventRequest, MultipartFile[] files);
}
