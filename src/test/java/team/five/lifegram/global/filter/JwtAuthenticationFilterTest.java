package team.five.lifegram.global.filter;

import jakarta.servlet.ServletException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import team.five.lifegram.global.util.JwtUtils;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter();

    @Test
    @DisplayName("필터에서 토큰이 유효하지 않는 경우 테스트")
    void doFilterInternalInvalidTokenTest() throws ServletException, IOException {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        MockFilterChain mockFilterChain = new MockFilterChain();
        String token = JwtUtils.generateToken(1L).accessToken();
        mockHttpServletRequest.addHeader("Authorization", "Bearer " + token.substring(0, token.length() - 2));

        jwtAuthenticationFilter.doFilterInternal(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);

        assertEquals((String) mockHttpServletRequest.getAttribute("unauthorization"), "401-001 인증키 유효하지 않음.");
    }

    @Test
    @DisplayName("필터에서 토큰이 비어있는 경우 테스트")
    void doFilterInternalNotHasTextTest() throws ServletException, IOException {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        MockFilterChain mockFilterChain = new MockFilterChain();

        jwtAuthenticationFilter.doFilterInternal(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);

        assertEquals((String) mockHttpServletRequest.getAttribute("unauthorization"), "401 인증키 없음.");
    }

    @Test
    @DisplayName("필터 통과 테스트")
    void doFilterInternalSuccessTest() throws ServletException, IOException {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        MockFilterChain mockFilterChain = new MockFilterChain();
        String token = JwtUtils.generateToken(1L).accessToken();
        mockHttpServletRequest.addHeader("Authorization", "Bearer " + token);

        jwtAuthenticationFilter.doFilterInternal(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    }
}