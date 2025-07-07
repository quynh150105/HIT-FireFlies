package com.example.hit_networking_base.controller;

import com.example.hit_networking_base.base.RestApiV1;
import com.example.hit_networking_base.base.VsResponseUtil;
import com.example.hit_networking_base.constant.UrlConstant;
import com.example.hit_networking_base.domain.dto.request.EventRequest;
import com.example.hit_networking_base.domain.dto.request.EventUpdateRequest;
import com.example.hit_networking_base.domain.dto.response.EventPageResponseDTO;
import com.example.hit_networking_base.domain.dto.response.EventResponse;
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

    @Operation(summary = "Tạo sự kiện mới")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tạo thành công",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ"),
            @ApiResponse(responseCode = "403", description = "Không có quyền"),
            @ApiResponse(responseCode = "500", description = "Lỗi hệ thống")
    })
    @PostMapping(value = UrlConstant.Event.CREATE_EVENT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createEvent(@ModelAttribute @Valid EventRequest eventRequest){
        return VsResponseUtil.success(eventService.createEvent(eventRequest, eventRequest.getFiles()));
    }

    @Operation(summary = "Danh sách các sự kiện")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy danh sách sự kiện thành công",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventPageResponseDTO.class))), // giả sử bạn có 1 class chứa danh sách có phân trang
            @ApiResponse(responseCode = "403", description = "Không có quyền"),
            @ApiResponse(responseCode = "500", description = "Lỗi hệ thống")
    })
    @GetMapping(UrlConstant.Event.GET_EVENTS)
    public ResponseEntity<?> getAllEvent(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return VsResponseUtil.success(eventService.getPageAllEvent(page, size));
    }

    @Operation(summary = "Sửa sự kiện theo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sửa thành công",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ"),
            @ApiResponse(responseCode = "403", description = "Không có quyền"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy sự kiện"),
            @ApiResponse(responseCode = "500", description = "Lỗi hệ thống")
    })
    @PutMapping(UrlConstant.Event.UPDATE_EVENT)
    public ResponseEntity<?> updateEvent(
            @PathVariable long eventId,
            @RequestBody @Valid EventUpdateRequest request) {

        return VsResponseUtil.success(eventService.updateEvent(eventId, request));
    }
}
