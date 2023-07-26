package team.five.lifegram.domain.comment.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team.five.lifegram.domain.comment.repository.CommentRepository;
import team.five.lifegram.domain.post.repository.PostRepository;
import team.five.lifegram.domain.user.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

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
    void writeComment() {
        Long userId;
        Long postId;
        String content = "";


    }

    @Test
    void deleteComment() {
    }
}