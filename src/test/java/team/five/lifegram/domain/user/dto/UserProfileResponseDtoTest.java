package team.five.lifegram.domain.user.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserProfileResponseDtoTest {

    @Test
    public void testUserProfileResponseDto() {
        // Given
        String userName = "JohnDoe";
        String img_url = "example.jpg";
        int postCount = 12;

        // When
        UserProfileResponseDto dto = new UserProfileResponseDto(userName, img_url, postCount);

        // Then
        assertEquals(userName, dto.getUserName());
        assertEquals("images/profile/example.jpg", dto.getProfileImgUrl());
        assertEquals(postCount, dto.getPostCount());
    }
}