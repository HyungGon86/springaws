package study.springaws.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.springaws.domain.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
