package team.five.lifegram.domain.post.dto;

import lombok.Builder;
import lombok.Getter;
import team.five.lifegram.domain.post.entity.Post;

import java.time.LocalDateTime;

import static team.five.lifegram.global.util.HttpUtils.parseS3Url;

@Getter
@Builder
public class PostResponseDto {
    private Long postId;
    private String writerImgUrl;
    private String postImgUrl;
    private String content;
    private Long likeCount;
    private boolean isLike;
    private Long commentCount;
    private String writer;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PostResponseDto of(Post post, boolean isLike){
        return PostResponseDto.builder()
                .postId(post.getId())
                .writerImgUrl(parseS3Url("images/profile",post.getUser().getImg_url()))
                .postImgUrl(parseS3Url("images/post",post.getImage_url()))
                .content(post.getContent())
                .likeCount(Long.valueOf(post.getLikes().size()))
                .isLike(isLike)
                .commentCount(Long.valueOf(post.getComments().size()))
                .writer(post.getUser().getUserName())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
