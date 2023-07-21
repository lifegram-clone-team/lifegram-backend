package team.five.lifegram.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.five.lifegram.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
