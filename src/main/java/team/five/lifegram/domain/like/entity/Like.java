package team.five.lifegram.domain.like.entity;

import jakarta.persistence.*;
import lombok.*;
import team.five.lifegram.domain.post.entity.Post;
import team.five.lifegram.domain.user.entity.User;

@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "`like`")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //TODO fetch LAZY 걸어주셔야 합니다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

}

