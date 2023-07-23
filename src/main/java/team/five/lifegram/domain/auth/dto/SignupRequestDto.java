package team.five.lifegram.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SignupRequestDto(
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
        String email,
        @Pattern(regexp = "^[a-zA-z0-9_]{5,12}$")
        String userName,
        @Pattern(regexp = "^[a-zA-Z0-9]{8,16}$")
        String password
) {}
