package team.five.lifegram.domain.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import team.five.lifegram.domain.like.repository.LikeRepository;
import team.five.lifegram.domain.post.dto.DetailPostResponseDto;
import team.five.lifegram.domain.post.entity.Post;
import team.five.lifegram.domain.post.repository.PostRepository;
import team.five.lifegram.domain.post.service.PostService;
import team.five.lifegram.domain.user.dto.UserProfileResponseDto;
import team.five.lifegram.domain.user.entity.User;
import team.five.lifegram.domain.user.repository.UserRepository;
import team.five.lifegram.global.imageUpload.S3Upload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    PostRepository postRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    LikeRepository likeRepository;

    @Mock
    S3Upload s3Upload;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Jacoco Test")
    void jacocoTest(){
        System.out.println("Jacoco Test");
    }

    @Nested
    @DisplayName("유저 프로필 조회 테스트")
    class GetUserProfile {

        @Test
        @DisplayName("유저 프로필 조회 성공")
        void getUserProfileSuccessTest(){
            // given
            Long userId = 1L;
            User user = User.builder()
                    .id(userId)
                    .build();
            given(userRepository.findById(userId)).willReturn(Optional.ofNullable(user));
            UserService userService = new UserService(postRepository, userRepository, s3Upload);

            // when
            UserProfileResponseDto userProfile = userService.getUserProfile(userId);

            // then
            assertEquals(userProfile.getProfileImgUrl(), user.getImg_url());
        }

        @Test
        @DisplayName("유저 프로필 조회 시 해당 userId 유저 존재 하지 않음 실패")
        void getUserProfileNoUserTest() {
            // given
            Long userId = 1L;
            UserService userService = new UserService(postRepository, userRepository, s3Upload);

            // when
            Exception exception = assertThrows(IllegalArgumentException.class, ()->
                    userService.getUserProfile(userId));

            // then
            assertEquals("존재하지 않는 사용자 입니다.", exception.getMessage());
        }
    }
}