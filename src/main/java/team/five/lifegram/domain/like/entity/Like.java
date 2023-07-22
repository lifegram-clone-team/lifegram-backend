package team.five.lifegram.domain.like.entity;

import jakarta.persistence.*;
import lombok.*;
import team.five.lifegram.domain.post.entity.Post;
import team.five.lifegram.domain.user.entity.User;

@Getter
//@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Like(User user, Post post) {
        this.user = user;
        this.post = post;
    }

    /*    public static Like likeOf(User user, Post post){
        return Like.builder()
                .user(user)
                .post(post)
                .build();
    }*/

}

