package team.five.lifegram.domain.comment.dto;

import lombok.Getter;
import team.five.lifegram.domain.comment.entity.Comment;

import java.time.LocalDateTime;

import static team.five.lifegram.global.util.HttpUtils.parseS3Url;

@Getter
public class CommentResponseDto {
    private Long commentId;
    private String content;
    private String writer;
    private String writerImgUrl;
    private LocalDateTime createdAt;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.writerImgUrl = parseS3Url("images/profile",comment.getUser().getImg_url());
        this.content = comment.getContent();
        this.writer = comment.getUser().getUserName();
        this.createdAt = comment.getCreatedAt();
    }
}
