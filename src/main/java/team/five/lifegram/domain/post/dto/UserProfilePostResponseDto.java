package team.five.lifegram.domain.post.dto;

import lombok.Getter;
import team.five.lifegram.domain.post.entity.Post;

import static team.five.lifegram.global.util.HttpUtils.parseS3Url;

@Getter
public class UserProfilePostResponseDto {
    private Long postId;
    private String profileImgUrl;

    public UserProfilePostResponseDto(Post post) {
        this.postId = post.getId();
        this.profileImgUrl = parseS3Url("images/post",post.getImage_url());
    }
}
