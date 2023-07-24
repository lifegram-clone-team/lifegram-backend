package team.five.lifegram.domain.user.dto;

import lombok.Getter;

import static team.five.lifegram.global.util.HttpUtils.parseS3Url;

@Getter
public class UserProfileResponseDto {
    private String userName;
    private String profileImgUrl;
    private int postCount;

    public UserProfileResponseDto(String userName, String img_url, int postCount) {
        this.userName = userName;
        this.profileImgUrl = parseS3Url("images/profile",img_url);
        this.postCount = postCount;
    }
}
