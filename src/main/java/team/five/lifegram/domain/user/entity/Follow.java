package team.five.lifegram.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import team.five.lifegram.global.jacoco.Generated;
import team.five.lifegram.global.type.BaseTime;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Follow extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    private User toUser;
    @Generated
    protected Follow(){}

    public User getToUser(){
        return this.toUser;
    }
}
