package team.five.lifegram.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.five.lifegram.domain.post.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
