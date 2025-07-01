package com.example.hit_networking_base.service;


import com.example.hit_networking_base.domain.dto.request.EventUpdateRequest;
import com.example.hit_networking_base.domain.dto.response.EventResponse;
import com.example.hit_networking_base.domain.entity.Event;
import com.example.hit_networking_base.domain.entity.User;
import com.example.hit_networking_base.domain.mapstruct.EventMapper;
import com.example.hit_networking_base.exception.UnauthorizedException;
import com.example.hit_networking_base.repository.EventRepository;
import com.example.hit_networking_base.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventMapper eventMapper;

    @Transactional
    public EventResponse updateEvent(Integer eventId, EventUpdateRequest request, Integer currentUserId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found with id: " + eventId));

        if (!event.getCreatedBy().equals(currentUserId)) {
            throw new UnauthorizedException("You are not authorized to update this event");
        }

        eventMapper.updateEventFromRequest(request, event);
        Event updatedEvent = eventRepository.save(event);

        User creator = userRepository.findById(updatedEvent.getCreatedBy())
                .orElseThrow(() -> new NotFoundException("User not found"));

        EventResponse response = eventMapper.toEventResponse(updatedEvent);
        response.setCreatedByUsername(creator.getUsername());
        response.setCreatedByAvatar(creator.getAvatarUrl());

        return response;
    }
}