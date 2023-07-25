package team.five.lifegram.global.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import team.five.lifegram.global.Security.UserAuthentication;
import team.five.lifegram.global.util.JwtUtils;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    //TODO var 사용하지 않기. 타입 명확하게 명시하기.
    //TODO println은 삭제하기 log로 남기기
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            var jwt = getJwtFromRequest(request);
            if (StringUtils.hasText(jwt) && JwtUtils.validateToken(jwt)) {
                var userId = JwtUtils.parseUserId(jwt);
                var authentication = new UserAuthentication(userId);

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                if (!StringUtils.hasText(jwt)) {
                    request.setAttribute("unauthorization", "401 인증키 없음.");
                }else if (JwtUtils.validateToken(jwt)) {
                    request.setAttribute("unauthorization", "401-001 인증키 만료.");
                }
            }
        } catch (Exception ex) {
            System.out.println("Could not set user authentication in security context");
        }

        filterChain.doFilter(request, response);
    }

    // TODO 적당한 클래스를 만들어 이동해야 할듯.
    private String getJwtFromRequest(HttpServletRequest request) {
        var bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }
}