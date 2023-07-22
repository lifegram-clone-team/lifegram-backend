package team.five.lifegram.domain.auth.dto;

import jakarta.validation.constraints.Pattern;

public record LoginRequestDto(
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
        String email,
        @Pattern(regexp = "^[a-zA-Z0-9]{8,16}$")
        String password
) {
}
