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
import team.five.lifegram.domain.like.repository.LikeRepository;
import team.five.lifegram.domain.post.entity.Post;
import team.five.lifegram.domain.post.repository.PostRepository;
import team.five.lifegram.domain.user.dto.UserProfileResponseDto;
import team.five.lifegram.domain.user.dto.UserProfileSearchResponseDto;
import team.five.lifegram.domain.user.entity.Follow;
import team.five.lifegram.domain.user.entity.User;
import team.five.lifegram.domain.user.repository.FollowRepository;
import team.five.lifegram.domain.user.repository.UserRepository;
import team.five.lifegram.global.imageUpload.S3Upload;
import team.five.lifegram.global.util.HttpUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    PostRepository postRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    LikeRepository likeRepository;

    @Mock
    FollowRepository followRepository;

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
            UserService userService = new UserService(postRepository, userRepository, followRepository, s3Upload);

            // when
            UserProfileResponseDto userProfile = userService.getUserProfile(userId);

            // then

            assertNotNull(userProfile);
        }

        @Test
        @DisplayName("유저 프로필 조회 시 해당 userId 유저 존재 하지 않음 실패")
        void getUserProfileNoUserTest() {
            // given
            Long userId = 1L;
            UserService userService = new UserService(postRepository, userRepository,followRepository, s3Upload);

            // when
            Exception exception = assertThrows(IllegalArgumentException.class, ()->
                    userService.getUserProfile(userId));

            // then
            assertEquals("존재하지 않는 사용자 입니다.", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("팔로우 중인 유저")
    class FindFollowingUser {

        @Test
        @DisplayName("FindFollowingUser 성공 테스트")
        void findFollowingUserSuccessTest() {
            Long userId = 1L;
            User user = mock(User.class);
            given(userRepository.findById(userId)).willReturn(Optional.ofNullable(user));

            UserService userService = new UserService(postRepository,userRepository,followRepository,s3Upload);
            List<Follow> follows = new ArrayList<>();
            Follow follow = Follow.builder()
                    .fromUser(user)
                    .toUser(user)
                    .build();
            follows.add(follow);
            given(user.getFollowing()).willReturn(follows);

            userService.findFollowingUser(userId);
        }

        @Test
        @DisplayName("FindFollowingUser 해당 userId유저 없음 테스트")
        void findFollowingUserNoUserTest() {
            //given
            Long userId = 1L;
            UserService userService = new UserService(postRepository,userRepository,followRepository,s3Upload);

            //when
            IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () ->
                    userService.findFollowingUser(userId));

            //then
            assertEquals("존재하지 않는 사용자 입니다.", illegalArgumentException.getMessage());
        }
    }

    @Nested
    @DisplayName("followUser")
    class FollowUser {

        @Test
        @DisplayName("FollowUser 성공 새로운 팔로우 테스트")
        void followUserNewFollowSuccessTest() {
            // given
            Long fromUserId = 1L;
            Long toUserId = 2L;

            User fromUser = mock(User.class);
            given(userRepository.findById(fromUserId)).willReturn(Optional.ofNullable(fromUser));
            User toUser = mock(User.class);
            given(userRepository.findById(toUserId)).willReturn(Optional.ofNullable(toUser));

            Follow follow = null;
            given(followRepository.findByFromUserAndToUser(fromUser, toUser)).willReturn(Optional.ofNullable(follow));

            UserService userService = new UserService(postRepository, userRepository, followRepository, s3Upload);

            // when
            userService.followUser(fromUserId, toUserId);

        }

        @Test
        @DisplayName("FollowUser 성공 팔로우 취소 테스트")
        void followUserCancelFollowSuccessTest() {
            // given
            Long fromUserId = 1L;
            Long toUserId = 2L;

            User fromUser = mock(User.class);
            given(userRepository.findById(fromUserId)).willReturn(Optional.ofNullable(fromUser));
            User toUser = mock(User.class);
            given(userRepository.findById(toUserId)).willReturn(Optional.ofNullable(toUser));

            Follow follow = Follow.builder()
                    .fromUser(fromUser)
                    .toUser(toUser)
                    .build();
            Optional<Follow> optionalFollow = Optional.of(follow);
            given(followRepository.findByFromUserAndToUser(fromUser, toUser)).willReturn(optionalFollow);

            UserService userService = new UserService(postRepository, userRepository, followRepository, s3Upload);

            // when
            userService.followUser(fromUserId, toUserId);

        }

        @Test
        @DisplayName("FollowUser NoFromUser Test")
        void followUserNoFromUserTest() {
            // given
            Long fromUserId = 1L;
            Long toUserId = 2L;

            UserService userService = new UserService(postRepository, userRepository, followRepository, s3Upload);

            // when
            IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () ->
                    userService.followUser(fromUserId, toUserId));

            // then
            assertEquals("다시 시도해 주세요", illegalArgumentException.getMessage());
        }

        @Test
        @DisplayName("FollowUser NoToUser Test")
        void followUserNoToUserTest() {
            // given
            Long fromUserId = 1L;
            Long toUserId = 2L;

            User fromUser = mock(User.class);
            given(userRepository.findById(fromUserId)).willReturn(Optional.ofNullable(fromUser));

            UserService userService = new UserService(postRepository, userRepository, followRepository, s3Upload);

            // when
            IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () ->
                    userService.followUser(fromUserId, toUserId));

            // then
            assertEquals("팔로우 하려는 유저가 존재하지 않습니다.", illegalArgumentException.getMessage());
        }
    }

    @Nested
    @DisplayName("유저 프로필 업데이트 테스트")
    class UserProfileUpdateTest {

        @Test
        @DisplayName("유저 프로필 이미지 파일이 있는 경우 테스트")
        void userProfileHasImageTest() throws IOException {
            String fileName = "testImage";
            String contentType = "JPG";
            String filePath = "src/test/resources/testImages/"+fileName+"."+contentType;
            FileInputStream fileInputStream = new FileInputStream(filePath);
            MockMultipartFile multipartFile = new MockMultipartFile("images",
                    fileName + "." + contentType,
                    contentType,
                    fileInputStream);
            Long userId = 1L;
            User user = User.builder().posts(new ArrayList<Post>()).build();
            UserService userService = new UserService(postRepository, userRepository, followRepository, s3Upload);
            given(userRepository.findById(userId)).willReturn(Optional.of(user));
            given(s3Upload.uploadFiles(multipartFile, "images/profile")).willReturn("12345.jpg");

            UserProfileResponseDto userProfileResponseDto = userService.updateUserProfile(multipartFile, userId);

            assertEquals(userProfileResponseDto.getProfileImgUrl(), HttpUtils.parseS3Url("images/profile" ,"12345.jpg"));
        }

        @Test
        @DisplayName("유저 프로필 이미지 파일이 null인 경우 테스트")
        void userProfileNullImageTest() throws IOException {
            MockMultipartFile multipartFile = null;
            Long userId = 1L;
            User user = User.builder().posts(new ArrayList<Post>()).build();
            UserService userService = new UserService(postRepository, userRepository, followRepository, s3Upload);
            given(userRepository.findById(userId)).willReturn(Optional.of(user));

            UserProfileResponseDto userProfileResponseDto = userService.updateUserProfile(multipartFile, userId);

            assertEquals(userProfileResponseDto.getProfileImgUrl(), HttpUtils.parseS3Url("images/profile" ,"default.jpeg"));
        }

        @Test
        @DisplayName("유저 프로필 이미지 파일이 데이터가 비어있는 경우 테스트")
        void userProfileDataEmptyImageTest() throws IOException {
            byte[] noDataByte = "".getBytes();
            MockMultipartFile multipartFile = new MockMultipartFile("noDataImage", noDataByte);
            Long userId = 1L;
            User user = User.builder().posts(new ArrayList<Post>()).build();
            UserService userService = new UserService(postRepository, userRepository, followRepository, s3Upload);
            given(userRepository.findById(userId)).willReturn(Optional.of(user));

            UserProfileResponseDto userProfileResponseDto = userService.updateUserProfile(multipartFile, userId);

            assertEquals(userProfileResponseDto.getProfileImgUrl(), HttpUtils.parseS3Url("images/profile" ,"default.jpeg"));
        }

        @Test
        @DisplayName("유저 프로필 사용자가 존재하지 않는 경우 테스트")
        void profileNotUserTest() throws IOException {
            MockMultipartFile multipartFile = null;
            Long userId = 1L;
            UserService userService = new UserService(postRepository, userRepository, followRepository, s3Upload);

            Exception ex = assertThrows(IllegalArgumentException.class, () -> userService.updateUserProfile(multipartFile, userId));

            assertEquals(ex.getMessage(), "존재하지 않는 사용자 입니다.");
        }

        @Test
        @DisplayName("유저 프로필 이미지 업로드 실패하는 경우 테스트")
        void profileUploadFailTest() throws IOException {
            String fileName = "testImage";
            String contentType = "JPG";
            String filePath = "src/test/resources/testImages/"+fileName+"."+contentType;
            FileInputStream fileInputStream = new FileInputStream(filePath);
            MockMultipartFile multipartFile = new MockMultipartFile("images",
                    fileName + "." + contentType,
                    contentType,
                    fileInputStream);
            Long userId = 1L;
            User user = User.builder().posts(new ArrayList<Post>()).build();
            UserService userService = new UserService(postRepository, userRepository, followRepository, s3Upload);
            given(userRepository.findById(userId)).willReturn(Optional.of(user));
            given(s3Upload.uploadFiles(multipartFile, "images/profile")).willThrow(new IOException());

            Exception ex = assertThrows(RuntimeException.class, () -> userService.updateUserProfile(multipartFile, userId));

            assertEquals(ex.getCause().getClass(), IOException.class);
        }
    }

    @Nested
    @DisplayName("유저 검색 테스트")
    class UserFindTest {

        @Test
        @DisplayName("유저 검색에 성공한 경우 테스트")
        void userProfileFindUsersTest() throws IOException {
            Long userId = 1L;
            String qName = "test";
            User user = mock(User.class);
            List<User> users = new ArrayList<>();
            users.add(user);
            UserService userService = new UserService(postRepository, userRepository, followRepository, s3Upload);
            given(userRepository.findById(userId)).willReturn(Optional.of(user));
            given(userRepository.findByUserNameContaining(qName)).willReturn(users);

            List<UserProfileSearchResponseDto> userProfileSearchResponseDtos = userService.findUser(qName, userId);

            assertNotNull(userProfileSearchResponseDtos);
            assertEquals(userProfileSearchResponseDtos.size(), 1);
        }

        @Test
        @DisplayName("유저 검색에서 유효한 유저가 아닌 경우 테스트")
        void userProfileInvalidUserTest() throws IOException {
            Long userId = 1L;
            String qName = "test";
            UserService userService = new UserService(postRepository, userRepository, followRepository, s3Upload);

            Exception ex = assertThrows(IllegalArgumentException.class,
                    () -> userService.findUser(qName, userId));

            assertEquals(ex.getMessage(), "존재하지 않는 사용자 입니다.");
        }
    }
}