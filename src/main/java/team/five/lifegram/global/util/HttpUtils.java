package team.five.lifegram.global.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpUtils {
    public static String S3_URL = "https://lifegram-image.s3.ap-northeast-2.amazonaws.com";
    public static String parseS3Url(String base, String imageName){
        return S3_URL + "/" + base + "/" + imageName;
    }
}
