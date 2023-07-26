package team.five.lifegram.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import team.five.lifegram.domain.user.entity.User;
import team.five.lifegram.global.util.HttpUtils;

@Getter
@Builder
public class UserProfileSearchResponseDto {
    private Long userId;
    private String userName;
    private String profileImgUrl;
    private boolean isFollowing;

    public static UserProfileSearchResponseDto of(User user, boolean isFollowing){
        return UserProfileSearchResponseDto.builder()
                .userId(user.getId())
                .userName(user.getUserName())
                .profileImgUrl(HttpUtils.parseS3Url("images/profile",user.getImg_url()))
                .isFollowing(isFollowing)
                .build();
    }
}
