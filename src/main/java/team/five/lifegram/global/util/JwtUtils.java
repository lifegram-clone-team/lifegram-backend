package team.five.lifegram.global.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import team.five.lifegram.domain.auth.dto.TokenResponseDto;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Map;

public class JwtUtils {
    private static final String secretKey = "LifeGramKeyqazwsxedcddedwdwedewdwedwedeededeqwrer";
    private static final Key SIGNING_KEY = getSigningKey();
    private static final Integer ACCESS_TOKEN_DURATION_SECONDS = 60 * 30;

    public static TokenResponseDto generateToken(Long userId) {
        Instant now = Instant.now();
        Instant expiryDateOfAccessToken = now.plusSeconds(ACCESS_TOKEN_DURATION_SECONDS);

        String accessToken = Jwts.builder()
                .setClaims(Map.of(
                        "userId", userId,
                        "iat", now.getEpochSecond(),
                        "exp", expiryDateOfAccessToken.getEpochSecond()
                ))
                .signWith(SIGNING_KEY, SignatureAlgorithm.HS256)
                .compact();

        return new TokenResponseDto(accessToken, expiryDateOfAccessToken.atZone(ZoneId.systemDefault()).toLocalDateTime());
    }

    public static Long parseUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SIGNING_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("userId", Long.class);
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SIGNING_KEY).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            System.out.println("Invalid JWT Token");
        } catch (ExpiredJwtException e) {
            System.out.println("Expired JWT Token");
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT Token");
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty.");
        }
        return false;
    }

    public static Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

}
