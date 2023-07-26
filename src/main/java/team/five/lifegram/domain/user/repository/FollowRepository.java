package team.five.lifegram.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.five.lifegram.domain.user.entity.Follow;
import team.five.lifegram.domain.user.entity.User;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFromUserAndToUser(User fromUser, User toUser);
    boolean existsByFromUserAndToUser(User fromUser, User toUser);
}
