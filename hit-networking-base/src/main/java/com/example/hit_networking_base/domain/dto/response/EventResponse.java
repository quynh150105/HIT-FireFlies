package com.example.hit_networking_base.domain.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class EventResponse {
    private Integer eventId;
    private String title;
    private String description;
    private LocalDate eventDate;
    private Integer createdBy;
    private LocalDateTime createdAt;
    private String createdByUsername;
    private String createdByAvatar;
}