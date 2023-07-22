package team.five.lifegram.domain.comment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import team.five.lifegram.domain.post.entity.Post;

@Entity
public class Comment {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}