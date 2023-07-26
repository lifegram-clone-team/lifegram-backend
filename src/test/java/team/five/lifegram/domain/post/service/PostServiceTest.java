package team.five.lifegram.domain.post.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import team.five.lifegram.domain.like.repository.LikeRepository;
import team.five.lifegram.domain.post.dto.PostRequestDto;
import team.five.lifegram.domain.post.entity.Post;
import team.five.lifegram.domain.post.repository.PostRepository;
import team.five.lifegram.domain.user.entity.User;
import team.five.lifegram.domain.user.repository.UserRepository;
import team.five.lifegram.global.imageUpload.S3Upload;

import java.io.FileInputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    PostRepository postRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    LikeRepository likeRepository;

    @Mock
    S3Upload s3Upload;

    @Test
    @DisplayName("Jacoco Test")
    void jacocoTest(){
        System.out.println("Jacoco Test");
    }
    @Nested
    @DisplayName("게시글 생성 테스트")
    class CreatePost {
        @Test
        @DisplayName("게시글 생성 정상")
        void createPostSuccessTest() throws Exception {
            // given
            Long userId = 1L;
            PostRequestDto postRequestDto = new PostRequestDto("testContent");
            String fileName = "testImage";
            String contentType = "JPG";
            String filePath = "src/test/resources/testImages/"+fileName+"."+contentType;
            FileInputStream fileInputStream = new FileInputStream(filePath);
            MockMultipartFile multipartFile = new MockMultipartFile("images",
                    fileName + "." + contentType,
                    contentType,
                    fileInputStream);
            User user = User.builder().build();
            PostService postService = new PostService(postRepository, userRepository, likeRepository, s3Upload);
            given(userRepository.findById(userId)).willReturn(Optional.of(user));

            // when
            postService.createPost(postRequestDto, multipartFile, userId);

            // then

        }

        @Test
        @DisplayName("게시글 생성시 해당 user_id 유저 존재 하지 않음 실패")
        void createPostNoUserTest() throws Exception{
            // given
            Long userId = 1L;
            PostRequestDto postRequestDto = new PostRequestDto("testContent");
            String fileName = "testImage";
            String contentType = "JPG";
            String filePath = "src/test/resources/testImages/"+fileName+"."+contentType;
            FileInputStream fileInputStream = new FileInputStream(filePath);
            MockMultipartFile multipartFile = new MockMultipartFile("images",
                    fileName + "." + contentType,
                    contentType,
                    fileInputStream);
            User user = User.builder().build();
            PostService postService = new PostService(postRepository, userRepository, likeRepository, s3Upload);

            // when
            Exception exception = assertThrows(IllegalArgumentException.class, ()-> {
                postService.createPost(postRequestDto, multipartFile, userId);
            });

            // then
            assertEquals("존재하지 않는 사용자 입니다.", exception.getMessage());
        }

        @Test
        @DisplayName("게시글 생성시 이미지 empty 실패")
        void createPostNoImageTest(){
            // given
            Long userId = 1L;
            PostRequestDto postRequestDto = new PostRequestDto("testContent");
            byte[] noDataByte = "".getBytes();
            MockMultipartFile multipartFile = new MockMultipartFile("noDataImage", noDataByte);
            User user = User.builder().build();
            PostService postService = new PostService(postRepository, userRepository, likeRepository, s3Upload);
            given(userRepository.findById(userId)).willReturn(Optional.of(user));

            // when
            Exception exception = assertThrows(IllegalArgumentException.class, ()-> {
                postService.createPost(postRequestDto, multipartFile, userId);
            });

            // then
            assertEquals("이미지 없이 게시글을 생성할 수 없습니다", exception.getMessage());
        }

        @Test
        @DisplayName("이미지 변환 실패")
        void createPostConvertFailTest() {

        }
    }

    @Nested
    @DisplayName("게시글 삭제 테스트")
    class DeletePost {

        @Test
        @DisplayName("게시글 삭제 테스트 성공")
        void deletePost() {
            // given
            Long postId = 1L;
            Long userId = 1L;
            User user = User.builder()
                    .id(userId)
                    .build();
            given(userRepository.findById(userId)).willReturn(Optional.of(user));
            Post post = new Post("", "", user);
            given(postRepository.findById(postId)).willReturn(Optional.of(post));

            // when
            PostService postService = new PostService(postRepository, userRepository, likeRepository, s3Upload);
            postService.deletePost(postId, userId);

            // then

        }
        @Test
        @DisplayName("게시글 삭제 요청자 존재 안함")
        void deletePostNoUserTest() {
            // given
            Long postId = 1L;
            Long userId = 1L;
            PostService postService = new PostService(postRepository, userRepository, likeRepository, s3Upload);

            // when
            Exception exception = assertThrows(IllegalArgumentException.class, ()->
                    postService.deletePost(postId, userId));

            // then
            assertEquals("존재하지 않는 사용자 입니다.", exception.getMessage());


        }

        @Test
        @DisplayName("게시글 존재 안함")
        void deletePostNoPostTest() {
            // given
            Long postId = 1L;
            Long userId = 1L;
            User user = User.builder().build();
            given(userRepository.findById(userId)).willReturn(Optional.of(user));
            PostService postService = new PostService(postRepository, userRepository, likeRepository, s3Upload);

            // when
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    postService.deletePost(postId, userId));

            // then
            assertEquals("게시글이 존재하지 않습니다.", exception.getMessage());
        }

        @Test
        @DisplayName("게시글 작성자와 게시글 삭제 요청자 다름")
        void deletePostUserNotMatchedTest() {
            // given
            Long postId = 1L;
            Long writerUserId = 1L;
            Long deleterUserId = 2L;
            User writerUser = User.builder()
                    .id(writerUserId)
                    .build();

            User deleterUser = User.builder()
                    .id(deleterUserId)
                    .build();

            given(userRepository.findById(deleterUserId)).willReturn(Optional.of(deleterUser));
            Post post = new Post("", "", writerUser);
            given(postRepository.findById(postId)).willReturn(Optional.of(post));
            PostService postService = new PostService(postRepository, userRepository, likeRepository, s3Upload);

            // when
            Exception exception = assertThrows(IllegalArgumentException.class, ()->
                    postService.deletePost(postId, deleterUser.getId()));

            // then
            assertEquals("이 게시글에 삭제 권한이 없습니다.", exception.getMessage());

        }
    }

}