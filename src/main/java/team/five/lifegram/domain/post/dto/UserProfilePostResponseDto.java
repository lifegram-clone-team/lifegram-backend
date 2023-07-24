package team.five.lifegram.domain.post.dto;

import lombok.Getter;
import team.five.lifegram.domain.post.entity.Post;

@Getter
public class UserProfilePostResponseDto {
    private Long postId;
    private String profileImgUrl;

    public UserProfilePostResponseDto(Post post) {
        this.postId = post.getId();
        this.profileImgUrl = post.getImage_url();
    }
}
