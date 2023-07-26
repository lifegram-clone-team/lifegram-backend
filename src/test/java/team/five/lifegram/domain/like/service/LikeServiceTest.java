package team.five.lifegram.domain.like.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team.five.lifegram.domain.like.entity.Like;
import team.five.lifegram.domain.like.repository.LikeRepository;
import team.five.lifegram.domain.post.entity.Post;
import team.five.lifegram.domain.post.repository.PostRepository;
import team.five.lifegram.domain.user.entity.User;
import team.five.lifegram.domain.user.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LikeServiceTest {

    @Mock
    LikeRepository likeRepository;
    @Mock
    PostRepository postRepository;
    @Mock
    UserRepository userRepository;

    @Test
    @DisplayName("좋아요 성공")
    void likeSuccessTest(){
        // given
        Long postId = 1L;
        Long userId = 1L;

        Post post = new Post();
        User user = User.builder().build();

        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(postRepository.findById(postId)).willReturn(Optional.of(post));

        LikeService likeService = new LikeService(likeRepository, postRepository, userRepository);

        //when
        likeService.likePost(postId, userId);

        //then
        verify(likeRepository, times(1)).save(any(Like.class));
    }

    @Test
    @DisplayName("좋아요 실패 - 없는 게시물")
    void likeSuccessFailNotPost(){
        Long postId = 1L;
        Long userId = 1L;

        LikeService likeService = new LikeService(likeRepository, postRepository, userRepository);

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, ()->
                likeService.likePost(postId, userId));

        // then
        assertEquals("없는 게시글입니다.", exception.getMessage());

    }

    @Test
    @DisplayName("좋아요 실패 - 없는 사용자")
    void likeSuccessFailNotUser(){
        Long postId = 1L;
        Long userId = 1L;

        Post post = new Post();

        given(postRepository.findById(postId)).willReturn(Optional.of(post));

        LikeService likeService = new LikeService(likeRepository, postRepository, userRepository);

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, ()->
                likeService.likePost(postId, userId));

        // then
        assertEquals("없는 사용자입니다.", exception.getMessage());

    }

}