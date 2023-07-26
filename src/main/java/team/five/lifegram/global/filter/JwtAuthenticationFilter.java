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
import team.five.lifegram.global.util.HttpUtils;
import team.five.lifegram.global.util.JwtUtils;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    //TODO var 사용하지 않기. 타입 명확하게 명시하기.
    //TODO println은 삭제하기 log로 남기기
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var jwt = HttpUtils.getJwtFromRequest(request);

        if (StringUtils.hasText(jwt) && JwtUtils.validateToken(jwt)) {
            var userId = JwtUtils.parseUserId(jwt);
            var authentication = new UserAuthentication(userId);

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            if (!StringUtils.hasText(jwt)) {
                request.setAttribute("unauthorization", "401 인증키 없음.");
            } else {
                request.setAttribute("unauthorization", "401-001 인증키 유효하지 않음.");
            }
        }

        filterChain.doFilter(request, response);
    }
}