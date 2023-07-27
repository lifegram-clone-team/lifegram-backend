package team.five.lifegram.domain.post.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import team.five.lifegram.domain.post.entity.Post;
import team.five.lifegram.domain.user.entity.User;
import team.five.lifegram.global.util.HttpUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class PostResponseDtoTest {
    @Test
    @DisplayName("PostResponseDto 테스트")
    void postResponseDtoTest() {
        User user = User.builder().build();
        Post post = new Post("testImageUrl", "testContent", user);

        PostResponseDto postResponseDto = PostResponseDto.of(post, false);

        assertEquals(postResponseDto.getPostId(), post.getId());
        assertEquals(postResponseDto.getWriterImgUrl(), HttpUtils.parseS3Url("images/profile", post.getUser().getImg_url()));
        assertEquals(postResponseDto.getPostImgUrl(), HttpUtils.parseS3Url("images/post", post.getImage_url()));
        assertEquals(postResponseDto.getContent(), post.getContent());
        assertEquals(postResponseDto.getLikeCount(), post.getLikes().size());
        assertFalse(postResponseDto.isLike());
        assertEquals(postResponseDto.getCommentCount(), post.getComments().size());
        assertEquals(postResponseDto.getWriter(), post.getUser().getUserName());
        assertEquals(postResponseDto.getCreatedAt(), post.getCreatedAt());
        assertEquals(postResponseDto.getUpdatedAt(), post.getUpdatedAt());
    }

}