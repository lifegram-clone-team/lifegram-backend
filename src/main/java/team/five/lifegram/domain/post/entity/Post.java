package team.five.lifegram.domain.post.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team.five.lifegram.domain.comment.entity.Comment;
import team.five.lifegram.domain.like.entity.Like;
import team.five.lifegram.domain.user.entity.User;
import team.five.lifegram.global.type.BaseTime;

import java.util.ArrayList;
import java.util.List;

//TODO Entity에는 factory method or builder 패턴을 사용해서 객체를 생성한다.
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Post extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //TODO 변수명은 카멜케이스를 사용한다.
    @Column(nullable = false)
    private String image_url;

    @Column(length = 2200, nullable = false)
    private String content;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Like> likes = new ArrayList<>();


    public Post(String image_url, String content, User user) {
        this.image_url = image_url;
        this.content = content;
        this.user = user;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}