package team.five.lifegram.global.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class HttpUtilsTest {

    @Test
    @DisplayName("이미지 이름을 S3 URL로 변경 성공 테스 ")
    void parseS3Url() {
        //GIVE
        String fileName = "photo.img";
        String base = "photos";

        //WHEN
        String s3Url = HttpUtils.parseS3Url(base, fileName);

        //THEN
        Assertions.assertThat(s3Url).isEqualTo("https://lifegram-image.s3.ap-northeast-2.amazonaws.com/photos/photo.img");
    }

    @Test
    @DisplayName("request에서 jwt 얻어오기 성공 테스트")
    void getJwtFromRequestSuccessTest(){
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2OTAxNzU1MDMsInVzZXJJZCI6MjUsImV4cCI6MTY5MDE3NzMwM30.iTKE_HlW4mTrqGBthpaEqFpESCih9Y2iG39i2V0TrIA");

        String token = HttpUtils.getJwtFromRequest(mockHttpServletRequest);

        assertEquals(token, "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2OTAxNzU1MDMsInVzZXJJZCI6MjUsImV4cCI6MTY5MDE3NzMwM30.iTKE_HlW4mTrqGBthpaEqFpESCih9Y2iG39i2V0TrIA");
    }

    @Test
    @DisplayName("request에서 Authorization이 빈 텍스트여서 jwt 얻어오기 실패")
    void getJwtFromRequestNotTextTest(){
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.addHeader("Authorization", "");

        String token = HttpUtils.getJwtFromRequest(mockHttpServletRequest);

        assertNull(token);
    }

    @Test
    @DisplayName("request에서 Bearer 없어서 jwt 얻어오기 실패")
    void getJwtFromRequestNotBearerTest(){
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.addHeader("Authorization", "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2OTAxNzU1MDMsInVzZXJJZCI6MjUsImV4cCI6MTY5MDE3NzMwM30.iTKE_HlW4mTrqGBthpaEqFpESCih9Y2iG39i2V0TrIA");

        String token = HttpUtils.getJwtFromRequest(mockHttpServletRequest);

        assertNull(token);
    }
}