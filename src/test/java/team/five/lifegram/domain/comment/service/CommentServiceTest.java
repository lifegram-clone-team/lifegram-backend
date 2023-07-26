package team.five.lifegram.domain.comment.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team.five.lifegram.domain.comment.entity.Comment;
import team.five.lifegram.domain.comment.repository.CommentRepository;
import team.five.lifegram.domain.post.entity.Post;
import team.five.lifegram.domain.post.repository.PostRepository;
import team.five.lifegram.domain.user.entity.User;
import team.five.lifegram.domain.user.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    PostRepository postRepository;


    @Test
    @DisplayName("댓글 작성 정상")
    void writeCommentSuccessTest() {
        // given
        Long userId = 1L;
        Long postId = 1L;
        String content = "테스트 댓글";
        User user = User.builder().build();
        Post post = new Post();
        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(postRepository.findById(postId)).willReturn(Optional.of(post));
        CommentService commentService = new CommentService(commentRepository, userRepository, postRepository);

        //when
        commentService.writeComment(postId, content, userId);

        //then
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    @DisplayName("댓글 작성 실패 - 없는 사용자")
    void writeCommentNotUserFailTest() {
        // given
        Long userId = 1L;
        Long postId = 1L;
        String content = "테스트 댓글";
        User user = User.builder().build();
        Post post = new Post();
        given(postRepository.findById(postId)).willReturn(Optional.of(post));
        CommentService commentService = new CommentService(commentRepository, userRepository, postRepository);

        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            commentService.writeComment(postId, content, userId);
        });

        // then
        assertEquals("없는 사용자입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("댓글 작성 실패 - 없는 게시글")
    void writeCommentNotPostFailTest() {
        // given
        Long userId = 1L;
        Long postId = 1L;
        String content = "테스트 댓글";
        User user = User.builder().build();
        Post post = new Post();

        CommentService commentService = new CommentService(commentRepository, userRepository, postRepository);

        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            commentService.writeComment(postId, content, userId);
        });

        // then
        assertEquals("없는 게시물입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("댓글 삭제 성공")
    void deleteCommentSuccessTest() {
        // given
        Long userId = 1L;
        Long commentId = 1L;
        Long postId = 1L;

        User user = User.builder().id(userId).build();
        Post post = new Post();
        post.setId(postId);
        Comment comment = Comment.builder().post(post).user(user).build();

        CommentService commentService = new CommentService(commentRepository, userRepository, postRepository);

        given(commentRepository.findById(commentId)).willReturn(Optional.of(comment));

        //when
        commentService.deleteComment(postId, commentId, userId);

        //then
        verify(commentRepository, times(1)).delete(any(Comment.class));
    }

    @Test
    @DisplayName("댓글 삭제 실패 - 없는 댓글")
    void deleteCommentNotCommentFailTest() {
        // given
        Long userId = 1L;
        Long commentId = 1L;
        Long postId = 1L;

        User user = User.builder().id(userId).build();
        Post post = new Post();
        post.setId(postId);
        Comment comment = Comment.builder().post(post).user(user).build();

        CommentService commentService = new CommentService(commentRepository, userRepository, postRepository);

        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            commentService.deleteComment(postId, commentId, userId);
        });

        // then
        assertEquals("없는 댓글입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("댓글 삭제 실패 - 해당 게시글의 댓글이 아님")
    void deleteCommentDifferentPostFailTest() {
        // given
        Long userId = 1L;
        Long commentId = 1L;
        Long postId = 1L;

        User user = User.builder().id(userId).build();
        Post post = new Post();
        post.setId(2L);
        Comment comment = Comment.builder().post(post).user(user).build();

        CommentService commentService = new CommentService(commentRepository, userRepository, postRepository);

        given(commentRepository.findById(commentId)).willReturn(Optional.of(comment));

        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            commentService.deleteComment(postId, commentId, userId);
        });

        // then
        assertEquals("해당 게시글에 댓글이 아닙니다.", exception.getMessage());
    }

    @Test
    @DisplayName("댓글 삭제 실패 - 해당 게시글의 댓글이 아님")
    void deleteCommentDifferentUserFailTest() {
        // given
        Long userId = 1L;
        Long commentId = 1L;
        Long postId = 1L;

        User user = User.builder().id(2L).build();
        Post post = new Post();
        post.setId(postId);
        Comment comment = Comment.builder().post(post).user(user).build();

        CommentService commentService = new CommentService(commentRepository, userRepository, postRepository);

        given(commentRepository.findById(commentId)).willReturn(Optional.of(comment));

        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            commentService.deleteComment(postId, commentId, userId);
        });

        // then
        assertEquals("내가 작성한 댓글이 아닙니다.", exception.getMessage());
    }

}