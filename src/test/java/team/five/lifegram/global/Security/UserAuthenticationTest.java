package team.five.lifegram.global.Security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserAuthenticationTest {

    @Test
    @DisplayName("인증 정보 가져오기 성공 테스트")
    void setAuthenticated() {
        UserAuthentication userAuthentication = new UserAuthentication(1L);

        AuthPayload authPayload = userAuthentication.getPrincipal();

        assertEquals(1L, authPayload.userId());

    }

    @Test
    @DisplayName("Credentials null 값 테스트")
    void getCredentials() {
        UserAuthentication userAuthentication = new UserAuthentication(1L);

        Object credentials = userAuthentication.getCredentials();

        assertNull(credentials);
    }
}