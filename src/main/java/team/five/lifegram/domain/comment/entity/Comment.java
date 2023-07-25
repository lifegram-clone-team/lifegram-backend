package team.five.lifegram.domain.comment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import team.five.lifegram.domain.post.entity.Post;
import team.five.lifegram.domain.user.entity.User;
import team.five.lifegram.global.type.BaseTime;

import java.time.LocalDateTime;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Comment extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1024, nullable = false)
    private  String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //TODO 순환참조가 DTO를 사용하면 일어날 일이 거의 없습니다. (JsonIgnore) 한 번 확인해보고 필요 없으면 삭제 부탁드립니다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    //TODO 팩토리 메서드 사용시 메서드 명에 반환 타입을 넣지 않는다.
    public static Comment commentOf(Post post, String content, User user){
        return Comment.builder()
                .post(post)
                .content(content)
                .user(user)
                .build();
    }

}