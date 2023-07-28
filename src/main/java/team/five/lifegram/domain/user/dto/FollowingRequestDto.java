package team.five.lifegram.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FollowingRequestDto {
    private Long userId;
}
