package team.five.lifegram.global.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpUtils {
    public static String S3_URL = "https://lifegram-image.s3.ap-northeast-2.amazonaws.com";
    public static String parseS3Url(String base, String imageName){
        return S3_URL + "/" + base + "/" + imageName;
    }

    public static String getJwtFromRequest(HttpServletRequest request) {
        var bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }
}
