package team.five.lifegram.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import team.five.lifegram.domain.user.entity.Follow;
import team.five.lifegram.global.util.HttpUtils;

@Getter
@Builder
public class FollowingResponseDto {
    private Long userId;
    private String userName;
    private String profileImgUrl;

    public static FollowingResponseDto of(Follow follow) {
        return FollowingResponseDto.builder()
                .userId(follow.getToUser().getId())
                .userName(follow.getToUser().getUserName())
                .profileImgUrl(HttpUtils.parseS3Url("images/profile", follow.getToUser().getImg_url()))
                .build();
    }
}
