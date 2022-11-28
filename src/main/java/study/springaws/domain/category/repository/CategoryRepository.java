package study.springaws.domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.springaws.domain.category.domain.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {

    List<Category> findByParentIsNotNull();
}
