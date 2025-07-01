package com.example.hit_networking_base.controller;

import com.example.hit_networking_base.domain.dto.request.EventUpdateRequest;
import com.example.hit_networking_base.domain.dto.response.*;
import com.example.hit_networking_base.domain.dto.response.EventResponse;
import com.example.hit_networking_base.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PutMapping("/{eventId}")
    public ResponseEntity<EventResponse> updateEvent(
            @PathVariable Integer eventId,
            @RequestBody @Valid EventUpdateRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        Integer currentUserId = Integer.parseInt(userDetails.getUsername());
        EventResponse response = eventService.updateEvent(eventId, request, currentUserId);
        return ResponseEntity.ok(response);
    }
}