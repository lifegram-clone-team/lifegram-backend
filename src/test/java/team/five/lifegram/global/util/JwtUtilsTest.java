package team.five.lifegram.global.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import team.five.lifegram.domain.auth.dto.TokenResponseDto;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilsTest {

    @Test
    @DisplayName("토큰 생성 Success")
    void generateTokenTest() {
        Long userId = 1L;

        TokenResponseDto tokenResponseDto = JwtUtils.generateToken(userId);

        assertNotNull(tokenResponseDto);
    }

    @Test
    @DisplayName("UserId 얻어오기 테스트")
    void parseUserIdTest() {
        String token = JwtUtils.generateToken(1L).accessToken();

        Long userId = JwtUtils.parseUserId(token);

        assertEquals(1L, userId);
    }

    @Test
    @DisplayName("토큰 유형성 검증 성공")
    void validateTokenSuccessTest() {
        String token = JwtUtils.generateToken(1L).accessToken();

        boolean isValidateToken = JwtUtils.validateToken(token);

        assertEquals(true, isValidateToken);
    }

    @Test
    @DisplayName("토큰 유형성 잘못된 토큰으로 인한 실패")
    void validateTokenInvalidFailTest() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2OTAzNTA4NTAsInVzZXJJZCI6MSwiaWF0IjoxNjkwMzQ3MjUwfQ.a3I_mHABXRWaNts67qH6Z-4dykudiG2GapZG-Hdhs";

        boolean isValidateToken = JwtUtils.validateToken(token);

        assertEquals(false, isValidateToken);
    }

    @Test
    @DisplayName("토큰 유효성 빈 값으로 인한 실패")
    void validateTokenEmptyFailTest() {
        String token = "";

        boolean isValidateToken = JwtUtils.validateToken(token);

        assertEquals(false, isValidateToken);
    }

    @Test
    @DisplayName("토큰 유효성 만료기간으로 실패")
    void validateTokenExpiredFailTest() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2OTAxNzU1MDMsInVzZXJJZCI6MjUsImV4cCI6MTY5MDE3NzMwM30.iTKE_HlW4mTrqGBthpaEqFpESCih9Y2iG39i2V0TrIA";

        boolean isValidateToken = JwtUtils.validateToken(token);

        assertEquals(false, isValidateToken);
    }
}