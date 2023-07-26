package team.five.lifegram.domain.auth.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import team.five.lifegram.domain.auth.dto.SignupRequestDto;
import team.five.lifegram.domain.user.entity.User;
import team.five.lifegram.domain.user.repository.UserRepository;
import team.five.lifegram.global.config.SecurityConfig;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    UserRepository userRepository;
    PasswordEncoder passwordEncoder = new SecurityConfig().passwordEncoder();

    @Test
    @DisplayName("회원가입 유저이름이 중복되는 경우 테스트")
    void signupDupUserNameTest() {
        AuthService authService = new AuthService(userRepository, passwordEncoder);
        SignupRequestDto signupRequestDto = new SignupRequestDto("","","");
        Optional<User> user = Optional.of(User.builder().build());
        given(userRepository.findByUserName(signupRequestDto.userName())).willReturn(user);
        given(userRepository.findByEmail(signupRequestDto.email())).willReturn(Optional.empty());

        Exception e = assertThrows(IllegalArgumentException.class, () -> authService.signup(signupRequestDto));

        assertEquals(e.getMessage(), "유저이름이 중복됩니다");
    }

    @Test
    @DisplayName("회원가입 이메일이 중복되는 경우 테스트")
    void signupDupEmailTest() {
        AuthService authService = new AuthService(userRepository, passwordEncoder);
        SignupRequestDto signupRequestDto = new SignupRequestDto("","","");
        Optional<User> user = Optional.of(User.builder().build());
        given(userRepository.findByUserName(signupRequestDto.userName())).willReturn(Optional.empty());
        given(userRepository.findByEmail(signupRequestDto.email())).willReturn(user);

        Exception e = assertThrows(IllegalArgumentException.class, () -> authService.signup(signupRequestDto));

        assertEquals(e.getMessage(), "이메일이 중복됩니다");
    }

    @Test
    @DisplayName("회원가입 성공 테스트")
    void signupSuccessTest() {
        AuthService authService = new AuthService(userRepository, passwordEncoder);
        SignupRequestDto signupRequestDto = new SignupRequestDto("test@naver.com","testUser","test12345");
        given(userRepository.findByUserName(signupRequestDto.userName())).willReturn(Optional.empty());
        given(userRepository.findByEmail(signupRequestDto.email())).willReturn(Optional.empty());

       assertDoesNotThrow(() -> authService.signup(signupRequestDto));
    }

    @Test
    void login() {
    }
}