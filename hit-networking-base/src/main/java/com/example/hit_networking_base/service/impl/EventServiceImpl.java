package com.example.hit_networking_base.service.impl;

import com.example.hit_networking_base.constant.ErrorMessage;
import com.example.hit_networking_base.constant.TargetType;
import com.example.hit_networking_base.domain.dto.request.EventRequest;
import com.example.hit_networking_base.domain.dto.request.EventUpdateRequest;
import com.example.hit_networking_base.domain.dto.response.*;
import com.example.hit_networking_base.domain.entity.Event;
import com.example.hit_networking_base.domain.entity.User;
import com.example.hit_networking_base.domain.mapstruct.EventMapper;
import com.example.hit_networking_base.domain.mapstruct.UserMapper;
import com.example.hit_networking_base.exception.NotFoundException;
import com.example.hit_networking_base.exception.UnauthorizedException;
import com.example.hit_networking_base.repository.EventRepository;
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
    private final UserMapper userMapper;
    private final EventMapper eventMapper;
    private final ImageService imageService;
    private final CommentPostService commentPostService;
    private final ReactionService reactionService;
    private final SendEmailService sendEmailService;

    @Override
    public EventResponseDTO createEvent(EventRequest eventRequest, MultipartFile[] files) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findUserByUsername(username);

        Event event = eventMapper.toEvent(eventRequest);
        event.setCreator(user);
        event.setCreatedAt(LocalDateTime.now());
        event.setCountComment(0L);
        event.setCountReaction(0L);
        eventRepository.save(event);

        List<String> image = Collections.emptyList();

        if (files != null && files.length > 0) {
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

        sendEmailService.sendEmailWhenCreatePost(userService.getAllUserToSendEmail());
        return eventResponseDTO;
    }

    @Override
    public Page<EventPostResponseDTO> getPageAllEvent(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Event> eventPage = eventRepository.findByDeletedAtIsNull(pageable);

        List<EventPostResponseDTO> dtos = eventPage.getContent().stream()
                .map(event -> {
                    EventPostResponseDTO dto = eventMapper.toEventPostResponse(event);
                    List<String> images = imageService.getUrlImage(event.getEventId(), TargetType.EVENT);
                    dto.setUrlImage(images);
                    dto.setEventId(event.getEventId());
                    dto.setCreator(userMapper.toUserPostResponseDTO(event.getCreator()));
                    return dto;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, eventPage.getTotalElements());
    }

    @Override
    public EventResponseDTO updateEvent(long eventId, EventUpdateRequest request) {
        Event event = findById(eventId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if (!event.getCreator().getUsername().equals(username)) {
            throw new UnauthorizedException("You are not authorized to update this event");
        }

        eventMapper.updateEventFromRequest(request, event);
        Event updatedEvent = eventRepository.save(event);

        return eventMapper.toEventResponseDTO(updatedEvent);
    }

    @Override
    public EventDetailResponseDTO getEventDetail(long eventId) {
        Event event = findById(eventId);

        List<String> urlImage = imageService.getUrlImage(eventId, TargetType.EVENT);
        List<CommentResponseDTO> comment = commentPostService.findCommentByTargetIdAndTargetType(eventId, TargetType.EVENT);
        List<ReactionResponseDTO> reaction = reactionService.findReactionByTargetIdAndTargetType(eventId, TargetType.EVENT);
        EventDetailResponseDTO eventDetailResponseDTO = eventMapper.toEventDetailResponseDTO(event);
        eventDetailResponseDTO.setImages(urlImage);
        eventDetailResponseDTO.setCommentResponseDTOS(comment);
        eventDetailResponseDTO.setReactionResponseDTOS(reaction);
        return eventDetailResponseDTO;
    }

    @Override
    public Event findById(Long id) {
        return eventRepository.findByEventIdAndDeletedAtIsNull(id).orElseThrow(()
                -> new NotFoundException(ErrorMessage.Event.ERR_NOT_FOUND_EVENT));
    }

    @Override
    public EventPostResponseDTO getPostEvent(Long id) {
        Event event = findById(id);
        EventPostResponseDTO eventPostResponseDTO = eventMapper.toEventPostResponse(event);
        eventPostResponseDTO.setUrlImage(imageService.getUrlImage(id, TargetType.EVENT));
        eventPostResponseDTO.setCreator(userMapper.toUserPostResponseDTO(event.getCreator()));
        return eventPostResponseDTO;
    }

    @Override
    public void countComment(Long id, TargetType targetType) {
        Event event = findById(id);
        if(targetType.equals(TargetType.CREATE)){
            event.setCountComment(event.getCountComment() + 1);
        } else {
            if(event.getCountComment()>0)
                event.setCountComment(event.getCountComment() - 1);
        }
        eventRepository.save(event);
    }
}
