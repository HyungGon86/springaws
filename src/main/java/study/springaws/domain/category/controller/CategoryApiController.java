package study.springaws.domain.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import study.springaws.domain.category.dto.CategoryEditForm;
import study.springaws.domain.category.service.CategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryApiController {

    private final CategoryService categoryService;

    @PostMapping("/edit")
    public void editCategoryRequest(@RequestBody List<CategoryEditForm> categoryList) {
        categoryService.editCategory(categoryList);
    }
}
