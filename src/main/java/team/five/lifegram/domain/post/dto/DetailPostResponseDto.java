package team.five.lifegram.domain.post.dto;

import lombok.Getter;
import team.five.lifegram.domain.comment.dto.CommentResponseDto;
import team.five.lifegram.domain.post.entity.Post;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class DetailPostResponseDto {
    private Long postId;
    private String writerImgUrl;
    private String imageUrl;
    private String content;
    private Long likeCount;
    private boolean isLike;
    private Long commentCount;
    private String writer;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CommentResponseDto> comments;

    public DetailPostResponseDto(Post post) {
        this.postId = post.getId();
        this.writerImgUrl = post.getUser().getImg_url();
        this.imageUrl = post.getImage_url();
        this.content = post.getContent();
        this.likeCount = 1L;
        this.isLike = false;
        this.commentCount = Long.valueOf(post.getCommentList().size());
        this.writer = post.getUser().getUserName();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
        this.comments = post.getCommentList().stream().map(CommentResponseDto::new).toList();
    }
}
