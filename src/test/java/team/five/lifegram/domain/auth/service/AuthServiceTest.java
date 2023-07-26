package team.five.lifegram.domain.auth.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import team.five.lifegram.domain.auth.dto.LoginRequestDto;
import team.five.lifegram.domain.auth.dto.SignupRequestDto;
import team.five.lifegram.domain.auth.dto.TokenResponseDto;
import team.five.lifegram.domain.user.entity.User;
import team.five.lifegram.domain.user.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;

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
    @DisplayName("로그인시 이메일 존재하지 않은 경우 테스트")
    void loginNotEmailTest() {
        AuthService authService = new AuthService(userRepository, passwordEncoder);
        LoginRequestDto loginRequestDto = new LoginRequestDto("","");
        given(userRepository.findByEmail(loginRequestDto.email())).willReturn(Optional.empty());

        Exception e = assertThrows(IllegalArgumentException.class, () -> authService.login(loginRequestDto));

        assertEquals(e.getMessage(), "이메일이 존재하지 않습니다");
    }

    @Test
    @DisplayName("로그인시 비밀번호가 유효하지 않은 경우 테스트")
    void loginInvalidPasswordTest() {
        AuthService authService = new AuthService(userRepository, passwordEncoder);
        LoginRequestDto loginRequestDto = new LoginRequestDto("","");
        User user = User.builder().build();
        given(userRepository.findByEmail(loginRequestDto.email())).willReturn(Optional.of(user));
        given(passwordEncoder.matches(loginRequestDto.password(),user.getPassword())).willReturn(false);

        Exception e = assertThrows(IllegalArgumentException.class, () -> authService.login(loginRequestDto));

        assertEquals(e.getMessage(), "비밀번호가 맞지 않습니다.");
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void loginSuccessTest() {
        AuthService authService = new AuthService(userRepository, passwordEncoder);
        LoginRequestDto loginRequestDto = new LoginRequestDto("","");
        User user = User.builder().id(1L).build();
        given(userRepository.findByEmail(loginRequestDto.email())).willReturn(Optional.of(user));
        given(passwordEncoder.matches(loginRequestDto.password(),user.getPassword())).willReturn(true);

        TokenResponseDto tokenResponseDto = authService.login(loginRequestDto);

        assertNotNull(tokenResponseDto);
    }
}