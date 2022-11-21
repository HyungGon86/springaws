package study.springaws.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.springaws.domain.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
