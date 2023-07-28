package team.five.lifegram.domain.post.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import team.five.lifegram.domain.post.entity.Post;
import team.five.lifegram.domain.user.entity.User;
import team.five.lifegram.global.util.HttpUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserProfilePostResponseDtoTest {

    @Test
    @DisplayName("UserProfilePostResponseDto 테스트")
    void userProfilePostResponseDtoTest() {
        User user = User.builder().build();
        Post post = Post.builder()
                .user(user)
                .build();

        UserProfilePostResponseDto userProfilePostResponseDto = new UserProfilePostResponseDto(post);

        assertEquals(userProfilePostResponseDto.getPostId(), post.getId());
        assertEquals(userProfilePostResponseDto.getProfileImgUrl(), HttpUtils.parseS3Url("images/post", post.getImage_url()));
    }


}