package study.springaws.domain.category.repository;

import study.springaws.domain.category.domain.Category;
import study.springaws.domain.category.dto.CategoryEditForm;

import java.util.List;

public interface CategoryRepositoryCustom {

    List<CategoryEditForm> superCategory();

    List<CategoryEditForm> subCategory();
}
