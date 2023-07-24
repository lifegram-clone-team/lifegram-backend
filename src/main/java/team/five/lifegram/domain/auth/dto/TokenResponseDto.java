package team.five.lifegram.domain.auth.dto;

import lombok.Builder;

import java.time.LocalDateTime;

public record TokenResponseDto(
        String accessToken,
        LocalDateTime expiredAt
) {}
