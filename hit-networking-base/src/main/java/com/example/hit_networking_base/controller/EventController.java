package com.example.hit_networking_base.controller;

import com.example.hit_networking_base.base.RestApiV1;
import com.example.hit_networking_base.base.VsResponseUtil;
import com.example.hit_networking_base.constant.UrlConstant;
import com.example.hit_networking_base.domain.dto.request.EventRequest;
import com.example.hit_networking_base.domain.dto.request.EventUpdateRequest;
import com.example.hit_networking_base.domain.dto.response.EventDetailResponseDTO;
import com.example.hit_networking_base.domain.dto.response.EventPostResponseDTO;
import com.example.hit_networking_base.domain.dto.response.EventResponseDTO;
import com.example.hit_networking_base.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestApiV1
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @Operation(summary = "Create a new event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Event created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = UrlConstant.Event.CREATE_EVENT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createEvent(@ModelAttribute @Valid EventRequest eventRequest){
        return VsResponseUtil.success(eventService.createEvent(eventRequest, eventRequest.getFiles()));
    }

    @Operation(summary = "Get a paginated list of events")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event list retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventPostResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(UrlConstant.Event.GET_EVENTS)
    public ResponseEntity<?> getAllEvent(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return VsResponseUtil.success(eventService.getPageAllEvent(page, size));
    }

    @Operation(summary = "Update an event by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping(UrlConstant.Event.UPDATE_EVENT)
    public ResponseEntity<?> updateEvent(
            @PathVariable long eventId,
            @RequestBody @Valid EventUpdateRequest request) {

        return VsResponseUtil.success(eventService.updateEvent(eventId, request));
    }

    @Operation(summary = "Admin get event detail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventDetailResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(UrlConstant.Event.GET_EVENT)
    public ResponseEntity<?> getEventDetail(@RequestParam long eventId){
        return VsResponseUtil.success(eventService.getEventDetail(eventId));
    }
}
