package team.five.lifegram.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team.five.lifegram.domain.auth.dto.SignupRequestDto;
import team.five.lifegram.domain.user.entity.User;
import team.five.lifegram.domain.user.repository.UserRepository;

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
}
