package com.example.hit_networking_base.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventUpdateRequest {
    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Event date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future(message="ngay su kien phai trong tuong lai")
    private LocalDateTime eventDate;

    @Schema(description = "Organizer", example = "Nguyễn Văn Nam")
    @NotBlank
    private String organizer;

    @Schema(description = "Location", example = "Trước toàn A1 Đại học Công nghiệp")
    @NotBlank
    private String location;
}