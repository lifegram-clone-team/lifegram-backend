package team.five.lifegram.domain.comment.dto;

import jakarta.persistence.Column;
import lombok.Getter;

@Getter
public class CommentRequestDto {
    @Column(nullable = false)
    String content;
}
