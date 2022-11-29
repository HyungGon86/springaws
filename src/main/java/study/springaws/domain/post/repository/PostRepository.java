package study.springaws.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.springaws.domain.category.domain.Category;
import study.springaws.domain.post.domain.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    Long countByCategoryIn(List<Category> categoryList);
}
