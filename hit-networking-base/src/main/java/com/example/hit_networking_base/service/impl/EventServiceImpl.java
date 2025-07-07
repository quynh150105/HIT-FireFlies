package com.example.hit_networking_base.service.impl;

import com.example.hit_networking_base.constant.ErrorMessage;
import com.example.hit_networking_base.constant.TargetType;
import com.example.hit_networking_base.domain.dto.request.EventRequest;
import com.example.hit_networking_base.domain.dto.request.EventUpdateRequest;
import com.example.hit_networking_base.domain.dto.response.*;
import com.example.hit_networking_base.domain.entity.Event;
import com.example.hit_networking_base.domain.entity.User;
import com.example.hit_networking_base.domain.mapstruct.EventMapper;
import com.example.hit_networking_base.exception.NotFoundException;
import com.example.hit_networking_base.exception.UnauthorizedException;
import com.example.hit_networking_base.repository.EventRepository;
import com.example.hit_networking_base.repository.UserRepository;
import com.example.hit_networking_base.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
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
    private final UserRepository userRepository;
    private final EventMapper eventMapper;
    private final ImageService imageService;
    private final CommentService commentService;
    private final ReactionService reactionService;

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

    @Override
    public Page<EventPageResponseDTO> getPageAllEvent(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Event> eventPage = eventRepository.findAll(pageable);

        List<EventPageResponseDTO> dtos = eventPage.getContent().stream()
                .map(event -> {
                    EventPageResponseDTO dto = eventMapper.toEventPageResponse(event);

                    // Gắn ảnh
                    List<String> images = imageService.getUrlImage(event.getEventId(), TargetType.EVENT);
                    dto.setImages(images); // Nếu bạn đặt tên field trong DTO là `image`

                    // Gắn comment
                    List<CommentResponseDTO> comments = commentService.findCommentByTargetIdAndTargetType(event.getEventId(), TargetType.EVENT);
                    dto.setCommentResponseDTOS(comments);

                    // Gắn reaction
                    List<ReactionResponseDTO> reactions = reactionService.findReactionByTargetIdAndTargetType(event.getEventId(), TargetType.EVENT);
                    dto.setReactionResponseDTOS(reactions);

                    return dto;
                })
                .collect(Collectors.toList());

        // Tùy yêu cầu, bạn có thể return list, hoặc bọc lại trong Page
        // Nếu cần giữ phân trang:
        return new PageImpl<>(dtos, pageable, eventPage.getTotalElements());
    }

    @Override
    public EventResponseDTO updateEvent(long eventId, EventUpdateRequest request) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found with id: " + eventId));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if (!event.getCreator().getUsername().equals(username)) {
            throw new UnauthorizedException("You are not authorized to update this event");
        }

        eventMapper.updateEventFromRequest(request, event);
        Event updatedEvent = eventRepository.save(event);

        return eventMapper.toEventResponseDTO(updatedEvent);
    }
}
