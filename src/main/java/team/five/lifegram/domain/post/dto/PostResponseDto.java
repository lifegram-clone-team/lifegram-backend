package team.five.lifegram.domain.post.dto;

import lombok.Builder;
import lombok.Getter;
import team.five.lifegram.domain.post.entity.Post;

import java.time.LocalDateTime;

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

    public static PostResponseDto of(Post post){
        return PostResponseDto.builder()
                .postId(post.getId())
                .writerImgUrl(post.getUser().getImg_url())
                .postImgUrl(post.getImage_url())
                .content(post.getContent())
                .likeCount(1L)
                .isLike(false)
                .commentCount(Long.valueOf(post.getComments().size()))
                .writer(post.getUser().getUserName())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
