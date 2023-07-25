package team.five.lifegram.global.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpUtilsTest {

    @Test
    void parseS3Url() {
        //GIVE
        String fileName = "photo.img";
        String base = "photos";

        //WHEN
        String s3Url = HttpUtils.parseS3Url(base, fileName);

        //THEN
        Assertions.assertThat(s3Url).isEqualTo("https://lifegram-image.s3.ap-northeast-2.amazonaws.com/photos/photo.img");
    }
}