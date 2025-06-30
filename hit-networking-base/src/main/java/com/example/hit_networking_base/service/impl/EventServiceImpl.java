package com.example.hit_networking_base.service.impl;

import com.example.hit_networking_base.constant.ErrorMessage;
import com.example.hit_networking_base.constant.TargetType;
import com.example.hit_networking_base.domain.dto.request.EventRequest;
import com.example.hit_networking_base.domain.dto.response.EventResponseDTO;
import com.example.hit_networking_base.domain.entity.Event;
import com.example.hit_networking_base.domain.entity.User;
import com.example.hit_networking_base.domain.mapstruct.EventMapper;
import com.example.hit_networking_base.repository.EventRepository;
import com.example.hit_networking_base.service.EventService;
import com.example.hit_networking_base.service.ImageService;
import com.example.hit_networking_base.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserService userService;
    private final EventMapper eventMapper;
    private final ImageService imageService;

    @Override
    public EventResponseDTO createEvent(EventRequest eventRequest, MultipartFile[] files) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findUserByUsername(username);

        Event event = eventMapper.toEvent(eventRequest);
        event.setCreator(user);
        event.setCreatedAt(LocalDateTime.now());
        eventRepository.save(event);

        List<String> image = Collections.emptyList();

        if (files != null && files.length > 0) {
            // Bỏ những file rỗng:
            List<MultipartFile> validFiles = Arrays.stream(files)
                    .filter(file -> file != null && !file.isEmpty())
                    .collect(Collectors.toList());

            if (!validFiles.isEmpty()) {
                try {
                    image = imageService.uploadImage(
                            validFiles.toArray(new MultipartFile[0]),
                            TargetType.EVENT,
                            event.getEventId()
                    );
                } catch (IOException e) {
                    throw new RuntimeException(ErrorMessage.Image.ERR_UPLOAD, e);
                }
            }
        }

        EventResponseDTO eventResponseDTO = eventMapper.toEventResponseDTO(event);
        eventResponseDTO.setImage(image);
        return eventResponseDTO;
    }
}
