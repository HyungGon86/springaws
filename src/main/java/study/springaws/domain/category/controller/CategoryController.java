package study.springaws.domain.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import study.springaws.domain.category.dto.CategoryEditForm;
import study.springaws.domain.category.service.CategoryService;
import study.springaws.domain.post.service.PostService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final PostService postService;

    @GetMapping("/edit")
    public String editCategory(Model model) {

        model.addAttribute("superCategory", categoryService.superCategory());
        model.addAttribute("subCategory", categoryService.subCategory());
        model.addAttribute("totalPostCount", postService.totalPostCount());

        return "category/edit-category";
    }

    @PostMapping("/edit")
    @ResponseBody
    public void editCategoryRequest(@RequestBody List<CategoryEditForm> categoryList) {

        categoryService.editCategory(categoryList);
    }
}
