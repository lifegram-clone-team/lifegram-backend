package team.five.lifegram.domain.user.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import team.five.lifegram.global.util.HttpUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FollowingResponseDtoTest {

    @Test
    @DisplayName("Getter Test")
    void getterTest(){
        Long userId = 1L;
        String userName = "username";
        String imgUrl = "url";

        FollowingResponseDto followingResponseDto = FollowingResponseDto.builder()
                .userId(userId)
                .userName(userName)
                .profileImgUrl(HttpUtils.parseS3Url("images/profile", imgUrl))
                .build();

        assertEquals(followingResponseDto.getUserId(), userId);
        assertEquals(followingResponseDto.getUserName(), userName);
        assertEquals(followingResponseDto.getProfileImgUrl(), "https://lifegram-image.s3.ap-northeast-2.amazonaws.com/images/profile/url");

    }



}