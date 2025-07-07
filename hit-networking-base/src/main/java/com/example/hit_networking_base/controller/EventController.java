package com.example.hit_networking_base.controller;

import com.example.hit_networking_base.base.RestApiV1;
import com.example.hit_networking_base.base.VsResponseUtil;
import com.example.hit_networking_base.constant.UrlConstant;
import com.example.hit_networking_base.domain.dto.request.EventRequest;
import com.example.hit_networking_base.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestApiV1
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping(value = UrlConstant.Event.CREATE_EVENT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createEvent(@ModelAttribute @Valid EventRequest eventRequest){
        return VsResponseUtil.success(eventService.createEvent(eventRequest, eventRequest.getFiles()));
    }

    @GetMapping(UrlConstant.Event.GET_EVENTS)
    public ResponseEntity<?> getAllEvent(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return VsResponseUtil.success(eventService.getPageAllEvent(page, size));
    }
}
