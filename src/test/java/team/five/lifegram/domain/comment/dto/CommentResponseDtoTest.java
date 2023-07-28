package team.five.lifegram.domain.comment.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import team.five.lifegram.domain.comment.entity.Comment;
import team.five.lifegram.domain.user.entity.User;
import team.five.lifegram.global.util.HttpUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommentResponseDtoTest {

    @Test
    @DisplayName("Comment Entity가 Dto로 변환이 정상적으로 동작하는지 테스트")
    void getCommentId() {
        User user = User.builder()
                .img_url("test.jpg")
                .userName("testUser")
                .build();
        Comment comment = Comment.builder()
                .id(1L)
                .content("testContent")
                .user(user)
                .build();

        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);

        assertEquals(commentResponseDto.getCommentId(), comment.getId());
        assertEquals(commentResponseDto.getCreatedAt(), comment.getCreatedAt());
        assertEquals(commentResponseDto.getContent(), comment.getContent());
        assertEquals(commentResponseDto.getWriter(), comment.getUser().getUserName());
        assertEquals(commentResponseDto.getWriterImgUrl(), HttpUtils.parseS3Url("images/profile" ,comment.getUser().getImg_url()));
    }
}