package study.springaws.domain.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.springaws.domain.category.domain.Category;
import study.springaws.domain.category.dto.CategoryEditForm;
import study.springaws.domain.category.repository.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryEditForm> superCategory() {
        return categoryRepository.superCategory();
    }

    public List<CategoryEditForm> subCategory() {
        return categoryRepository.subCategory();
    }
}
