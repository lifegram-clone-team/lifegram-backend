package team.five.lifegram.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.five.lifegram.domain.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
