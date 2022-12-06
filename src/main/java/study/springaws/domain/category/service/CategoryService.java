package study.springaws.domain.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.springaws.domain.category.domain.Category;
import study.springaws.domain.category.dto.CategoryEditForm;
import study.springaws.domain.category.repository.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Cacheable(value = "layoutCaching", key = "0")
    public List<CategoryEditForm> superCategory() {
        List<CategoryEditForm> superCategory = categoryRepository.superCategory();
        List<CategoryEditForm> subCategory = categoryRepository.subCategory();

        for (CategoryEditForm suCategory : superCategory) {
            Long count = 0L;

            for (CategoryEditForm sub : subCategory) {
                if (suCategory.getId().equals(sub.getParentId())) {
                    count += sub.getCount();
                }
            }
            suCategory.setCount(count);
        }

        return superCategory;
    }

    @Cacheable(value = "layoutCaching", key = "1")
    public List<CategoryEditForm> subCategory() {
        return categoryRepository.subCategory();
    }

    public List<Category> parentIsNotNull() {
        return categoryRepository.findByParentIsNotNull();
    }

    @Transactional
    public void editCategory(List<CategoryEditForm> categoryEditFormList) {

        categoryRepository.findAll();

        Category parent = null;
        for (CategoryEditForm form : categoryEditFormList) {

            if (form.getId() == null) {
                Category category = Category.builder()
                        .name(form.getName())
                        .tier(form.getTier())
                        .build();

                if (form.isParent()) {
                    parent = category;
                } else {
                    category.setParent(parent);
                }

                categoryRepository.save(category);
            } else {
                Category category = categoryRepository.findById(form.getId()).orElseThrow(() -> new IllegalStateException("카테고리를 찾을 수 없습니다."));

                category.changeName(form.getName());
                category.changeTier(form.getTier());

                if (form.isParent()) {
                    parent = category;
                    category.setParent(null);
                } else {
                    category.setParent(parent);
                }
            }
        }

        if (!categoryEditFormList.get(0).getDeleteList().isEmpty()) {
            categoryRepository.deleteAllById(categoryEditFormList.get(0).getDeleteList());
        }

    }


}
