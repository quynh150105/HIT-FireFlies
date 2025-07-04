package com.example.hit_networking_base.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    @Schema(description = "Username", example = "user1")
    @NotBlank(message = "Username must not be blank")
    private String username;

    @Schema(description = "Password", example = "Password123")
    @NotBlank(message = "Password must not be blank")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$",
            message = "Password must be at least 8 characters long, include at least one digit, one lowercase and one uppercase letter"
    )
    private String password;
}
