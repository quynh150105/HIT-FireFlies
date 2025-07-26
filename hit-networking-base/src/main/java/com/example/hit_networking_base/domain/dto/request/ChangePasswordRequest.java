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
public class ChangePasswordRequest {

    @Schema(
            description = "The user's current password. This field is required.",
            example = "OldPass123"
    )
    @NotBlank
    private String oldPassword;

    @Schema(
            description = "The new password the user wants to set. Must follow these rules:\n" +
                    "- At least 8 characters long\n" +
                    "- Contains at least one digit (0-9)\n" +
                    "- Contains at least one lowercase letter (a-z)\n" +
                    "- Contains at least one uppercase letter (A-Z)",
            example = "NewPass123"
    )
    @NotBlank(message = "Password must not be blank")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$",
            message = "Password must be at least 8 characters long, include at least one digit, one lowercase and one uppercase letter"
    )
    private String newPassword;

}
