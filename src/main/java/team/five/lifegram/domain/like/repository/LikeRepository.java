package team.five.lifegram.domain.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.five.lifegram.domain.like.entity.Like;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserIdAndPostId(Long userId, Long postId);

}
