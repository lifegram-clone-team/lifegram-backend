package team.five.lifegram.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.five.lifegram.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByUserNameContaining(String qName);
}
