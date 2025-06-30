package com.example.hit_networking_base.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {

    @Schema(description = "Title event", example = "Sự kiện kết nối HIT")
    @NotBlank
    private String title;

    @Schema(description = "Description", example = "Chào mừng tân sinh viên")
    private String description;

    @Schema(description = "Time date start", type = "string", example = "2025-06-30T10:00:00")
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime eventDate;

    @Schema(description = "Organizer", example = "Nguyễn Văn Nam")
    @NotBlank
    private String organizer;

    @Schema(description = "Location", example = "Trước toàn A1 Đại học Công nghiệp")
    @NotBlank
    private String location;

    @Schema(
            description = "List image",
            type = "array",
            implementation = MultipartFile.class
    )
    private MultipartFile[] files;
}
