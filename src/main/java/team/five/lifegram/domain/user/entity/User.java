package team.five.lifegram.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import team.five.lifegram.domain.post.entity.Post;
import team.five.lifegram.global.type.BaseTime;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String img_url;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @OneToMany(mappedBy = "fromUser")
    private List<Follow> following;

    @OneToMany(mappedBy = "toUser")
    private List<Follow> follower;

    public void updateImgUrl(String imgUrl) {
        this.img_url = imgUrl;
    }
}
