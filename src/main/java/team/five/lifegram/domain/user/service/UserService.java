package team.five.lifegram.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import team.five.lifegram.domain.post.repository.PostRepository;
import team.five.lifegram.domain.user.dto.FollowingResponseDto;
import team.five.lifegram.domain.user.dto.UserProfileResponseDto;
import team.five.lifegram.domain.user.dto.UserProfileSearchResponseDto;
import team.five.lifegram.domain.user.entity.Follow;
import team.five.lifegram.domain.user.entity.User;
import team.five.lifegram.domain.user.repository.FollowRepository;
import team.five.lifegram.domain.user.repository.UserRepository;
import team.five.lifegram.global.imageUpload.S3Upload;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final S3Upload s3Upload;

    @Transactional(readOnly = true)
    public UserProfileResponseDto getUserProfile(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->
                new IllegalArgumentException("존재하지 않는 사용자 입니다."));
        int postCount = postRepository.countByUser(user);
        UserProfileResponseDto userProfileResponseDto = new UserProfileResponseDto(user.getUserName(), user.getImg_url(), postCount);
        return userProfileResponseDto;
    }

    @Transactional
    public UserProfileResponseDto updateUserProfile(MultipartFile image, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->
                new IllegalArgumentException("존재하지 않는 사용자 입니다."));

        if(image == null || image.isEmpty()) {
            user.updateImgUrl("default.jpeg");
        }else {
            try {
                String imagePath = s3Upload.uploadFiles(image, "images/profile");
                user.updateImgUrl(imagePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
        return new UserProfileResponseDto(user.getUserName(), user.getImg_url(), user.getPosts().size());
    }

    @Transactional(readOnly = true)
    public List<UserProfileSearchResponseDto> findUser(String qName, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->
                new IllegalArgumentException("존재하지 않는 사용자 입니다."));
        List<User> users = userRepository.findByUserNameContaining(qName);
        return users.stream().map((finduser) -> UserProfileSearchResponseDto.of(finduser, followRepository.existsByFromUserAndToUser(user, finduser))).toList();
    }

    public void followUser(Long fromUserId, Long toUserId) {
        User fromUser = userRepository.findById(fromUserId).orElseThrow(()->
                new IllegalArgumentException("다시 시도해 주세요"));
        User toUser = userRepository.findById(toUserId).orElseThrow(()->
                new IllegalArgumentException("팔로우 하려는 유저가 존재하지 않습니다."));

        Optional<Follow> follow = followRepository.findByFromUserAndToUser(fromUser, toUser);

        if(follow.isPresent()) {
            followRepository.delete(follow.get());
        }else {
            Follow newFollow = Follow.builder()
                    .fromUser(fromUser)
                    .toUser(toUser)
                    .build();
            followRepository.save(newFollow);
        }
    }

    @Transactional(readOnly = true)
    public List<FollowingResponseDto> findFollowingUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->
                new IllegalArgumentException("존재하지 않는 사용자 입니다."));
        List<Follow> following = user.getFollowing();
        return following.stream().map(FollowingResponseDto::of).toList();


    }
}
