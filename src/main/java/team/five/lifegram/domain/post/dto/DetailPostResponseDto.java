package team.five.lifegram.domain.post.dto;

import lombok.Builder;
import lombok.Getter;
import team.five.lifegram.domain.comment.dto.CommentResponseDto;

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

}
