package com.example.hit_networking_base.service;

import com.example.hit_networking_base.domain.dto.request.EventRequest;
import com.example.hit_networking_base.domain.dto.response.EventPageResponseDTO;
import com.example.hit_networking_base.domain.dto.response.EventResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;

public interface EventService {
    EventResponseDTO createEvent(EventRequest eventRequest, MultipartFile[] files);
    Page<EventPageResponseDTO> getPageAllEvent(int page, int size);
}
