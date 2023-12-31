package team.five.lifegram.domain.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.five.lifegram.domain.post.entity.Post;
import team.five.lifegram.domain.user.entity.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    int countByUser(User user);

    Page<Post> findByUserId(Long userId, Pageable pageable);
}
