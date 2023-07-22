package team.five.lifegram.domain.post.dto;

import lombok.Getter;
import team.five.lifegram.domain.comment.entity.Comment;
import team.five.lifegram.domain.post.entity.Post;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostResponseDto {
    private Long postId;
    private String imageUrl;
    private String content;
    private Long likeCount;
    private boolean isLike;
    private Long commentCount;
    private String writer;
    private List<Comment> commentList;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public PostResponseDto(Post post) {
        this.postId = post.getId();
        this.imageUrl = post.getImageUrl();
        this.content = post.getContent();
        this.likeCount = 1L;
        this.isLike = false;
        this.commentCount = 1L;
        this.writer = "writer";
        this.commentList = post.getCommentList();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
    }
}
