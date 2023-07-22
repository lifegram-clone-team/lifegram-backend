package team.five.lifegram.domain.comment.dto;

import lombok.Getter;
import team.five.lifegram.domain.comment.entity.Comment;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long commentId;
    private String content;
    private String writer;
    private String writerImgUrl;
    private LocalDateTime createdAt;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.writerImgUrl = comment.getUser().getImg_url();
        this.content = comment.getContent();
        this.writer = comment.getUser().getUserName();
        this.createdAt = comment.getCreatedAt();
    }
}
