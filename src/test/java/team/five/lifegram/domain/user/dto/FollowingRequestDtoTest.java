package team.five.lifegram.domain.user.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FollowingRequestDtoTest {

    @Test
    public void testFollowingRequestDto() {

        Long userId = 123L;
        FollowingRequestDto dto = new FollowingRequestDto(userId);
        assertEquals(userId, dto.getUserId());
    }
}