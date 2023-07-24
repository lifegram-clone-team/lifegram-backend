package team.five.lifegram.domain.user.dto;

import lombok.Getter;

@Getter
public class UserProfileResponseDto {
    private String userName;
    private String profileImgUrl;
    private int postCount;

    public UserProfileResponseDto(String userName, String img_url, int postCount) {
        this.userName = userName;
        this.profileImgUrl = img_url;
        this.postCount = postCount;
    }
}
