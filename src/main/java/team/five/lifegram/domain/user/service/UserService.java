package team.five.lifegram.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.five.lifegram.domain.post.repository.PostRepository;
import team.five.lifegram.domain.user.dto.UserProfileResponseDto;
import team.five.lifegram.domain.user.entity.User;
import team.five.lifegram.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public UserProfileResponseDto getUserProfile(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->
                new IllegalArgumentException("존재하지 않는 사용자 입니다."));
        int postCount = postRepository.countByUser(user);
        UserProfileResponseDto userProfileResponseDto = new UserProfileResponseDto(user.getUserName(), user.getImg_url(), postCount);
        return userProfileResponseDto;
    }
}
