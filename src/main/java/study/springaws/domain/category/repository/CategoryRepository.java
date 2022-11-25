package study.springaws.domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.springaws.domain.category.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {
}
