package com.example.hit_networking_base.service;


import com.example.hit_networking_base.constant.TargetType;
import com.example.hit_networking_base.domain.dto.request.EventRequest;
import com.example.hit_networking_base.domain.dto.request.EventUpdateRequest;
import com.example.hit_networking_base.domain.dto.response.EventDetailResponseDTO;
import com.example.hit_networking_base.domain.dto.response.EventPostResponseDTO;
import com.example.hit_networking_base.domain.dto.response.EventResponseDTO;
import com.example.hit_networking_base.domain.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface EventService {
    EventResponseDTO createEvent(EventRequest eventRequest, MultipartFile[] files);
    Page<EventPostResponseDTO> getPageAllEvent(int page, int size);
    EventResponseDTO updateEvent(long eventId, EventUpdateRequest request);
    EventDetailResponseDTO getEventDetail(long eventId);
    Event findById(Long id);
    EventPostResponseDTO getPostEvent(Long id);
    void countComment(Long id, TargetType targetType);
    EventDetailResponseDTO deleteEvent(Long eventId);
}