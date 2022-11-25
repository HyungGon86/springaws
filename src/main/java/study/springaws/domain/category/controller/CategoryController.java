package study.springaws.domain.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import study.springaws.domain.category.service.CategoryService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/edit/category")
    public String editCategory(Model model) {

        model.addAttribute("superCategory", categoryService.superCategory());
        model.addAttribute("subCategory", categoryService.subCategory());

        return "category/edit-category";
    }
}
