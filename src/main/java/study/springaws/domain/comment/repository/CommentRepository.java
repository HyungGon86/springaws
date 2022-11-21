package study.springaws.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.springaws.domain.comment.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
