package team.five.lifegram.domain.user.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import team.five.lifegram.global.util.HttpUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserProfileSearchResponseDtoTest {

    @Test
    @DisplayName("Getter Test")
    void getterTest(){
        Long userId = 1L;
        String userName = "username";
        String imgUrl = "url";
        boolean isFollowing = false;

        UserProfileSearchResponseDto userProfileSearchResponseDto = UserProfileSearchResponseDto.builder()
                .userId(userId)
                .userName(userName)
                .profileImgUrl(HttpUtils.parseS3Url("images/profile",imgUrl))
                .isFollowing(isFollowing)
                .build();

        assertEquals(userProfileSearchResponseDto.getUserId(), userId);
        assertEquals(userProfileSearchResponseDto.getUserName(), userName);
        assertEquals(userProfileSearchResponseDto.getProfileImgUrl(), "https://lifegram-image.s3.ap-northeast-2.amazonaws.com/images/profile/url");
        assertEquals(userProfileSearchResponseDto.isFollowing(), isFollowing);

    }



}