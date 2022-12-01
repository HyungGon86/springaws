package study.springaws.domain.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import study.springaws.domain.category.service.CategoryService;
import study.springaws.domain.post.service.PostService;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final PostService postService;

    @GetMapping("/edit")
    public String editCategory() {
        return "category/edit-category";
    }
}
