package team.five.lifegram.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team.five.lifegram.domain.auth.dto.LoginRequestDto;
import team.five.lifegram.domain.auth.dto.SignupRequestDto;
import team.five.lifegram.domain.auth.dto.TokenResponseDto;
import team.five.lifegram.domain.user.entity.User;
import team.five.lifegram.domain.user.repository.UserRepository;
import team.five.lifegram.global.util.JwtUtils;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequestDto signupRequestDto) {
        User user = User.builder()
                .email(signupRequestDto.email())
                .userName(signupRequestDto.userName())
                .password(passwordEncoder.encode(signupRequestDto.password()))
                .img_url("image/profile/default.jpeg")
                .build();

        userRepository.save(user);
    }

    public TokenResponseDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.email()).orElseThrow(
                () -> new IllegalArgumentException("이메일이 존재하지 않습니다"));

        if(!passwordEncoder.matches(loginRequestDto.password(),user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 맞지 않습니다.");
        }

        return JwtUtils.generateToken(user.getId());
    }
}
