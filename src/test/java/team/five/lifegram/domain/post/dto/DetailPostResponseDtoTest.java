package team.five.lifegram.domain.post.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import team.five.lifegram.domain.comment.dto.CommentResponseDto;
import team.five.lifegram.domain.post.entity.Post;
import team.five.lifegram.domain.user.entity.User;
import team.five.lifegram.global.util.HttpUtils;

import static org.junit.jupiter.api.Assertions.*;
import static team.five.lifegram.global.util.HttpUtils.parseS3Url;

class DetailPostResponseDtoTest {

    @Test
    @DisplayName("Getter Test")
    void getterTest() {
        User user = User.builder().build();
        Post post = new Post("imageUrl", "testContent", user);

        DetailPostResponseDto detailPostResponseDto = DetailPostResponseDto.of(post, false);
        DetailPostResponseDto dprd = DetailPostResponseDto.builder()
                .postId(post.getId())
                .postImgUrl(parseS3Url("images/post" ,post.getImage_url()))
                .content(post.getContent())
                .likeCount(Long.valueOf(post.getLikes().size()))
                .isLike(false)
                .commentCount(Long.valueOf(post.getComments().size()))
                .writer(post.getUser().getUserName())
                .writerImgUrl(parseS3Url("images/profile", post.getUser().getImg_url()))
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .comments(post.getComments().stream().map(CommentResponseDto::new).toList())
                .build();

        assertEquals(detailPostResponseDto.getPostId(), post.getId());
        assertEquals(detailPostResponseDto.getPostImgUrl(), HttpUtils.parseS3Url("images/post",post.getImage_url()));
        assertEquals(detailPostResponseDto.getContent(), post.getContent());
        assertEquals(detailPostResponseDto.getLikeCount(), Long.valueOf(post.getLikes().size()));
        assertFalse(detailPostResponseDto.isLike());
        assertEquals(detailPostResponseDto.getCommentCount(), Long.valueOf(post.getComments().size()));
        assertEquals(detailPostResponseDto.getWriter(), post.getUser().getUserName());
        assertEquals(detailPostResponseDto.getWriterImgUrl(), parseS3Url("images/profile", post.getUser().getImg_url()));
        assertEquals(detailPostResponseDto.getCreatedAt(), post.getCreatedAt());
        assertEquals(detailPostResponseDto.getUpdatedAt(), post.getUpdatedAt());
        assertEquals(detailPostResponseDto.getComments(), post.getComments().stream().map(CommentResponseDto::new).toList());
    }
}