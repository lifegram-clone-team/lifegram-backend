package team.five.lifegram.domain.post.dto;

import lombok.Builder;
import lombok.Getter;
import team.five.lifegram.domain.comment.dto.CommentResponseDto;
import team.five.lifegram.domain.post.entity.Post;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class DetailPostResponseDto {
    private Long postId;
    private String postImgUrl;
    private String content;
    private Long likeCount;
    private boolean isLike;
    private Long commentCount;
    private String writer;
    private String writerImgUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CommentResponseDto> comments;

    public static DetailPostResponseDto of(Post post) {
        return DetailPostResponseDto.builder()
                .postId(post.getId())
                .postImgUrl(post.getImage_url())
                .content(post.getContent())
                .likeCount(1L)
                .isLike(false)
                .commentCount(Long.valueOf(post.getComments().size()))
                .writer(post.getUser().getUserName())
                .writerImgUrl(post.getUser().getImg_url())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .comments(post.getComments().stream().map(CommentResponseDto::new).toList())
                .build();
    }

}
