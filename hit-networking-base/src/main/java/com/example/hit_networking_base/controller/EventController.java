package com.example.hit_networking_base.controller;

import com.example.hit_networking_base.base.RestApiV1;
import com.example.hit_networking_base.base.VsResponseUtil;
import com.example.hit_networking_base.constant.UrlConstant;
import com.example.hit_networking_base.domain.dto.request.EventRequest;
import com.example.hit_networking_base.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestApiV1
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PutMapping(value = UrlConstant.Event.CREATE_EVENT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createEvent(@ModelAttribute @Valid EventRequest eventRequest){
        System.out.println("files == null ? " + (eventRequest.getFiles() == null));
        System.out.println("files.length = " + (eventRequest.getFiles() != null ? eventRequest.getFiles().length : 0));

        return VsResponseUtil.success(eventService.createEvent(eventRequest, eventRequest.getFiles()));
    }
}
