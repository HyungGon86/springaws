package study.springaws.global.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import study.springaws.domain.category.service.CategoryService;
import study.springaws.domain.post.service.PostService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;
    private final CategoryService categoryService;

    @GetMapping("/")
    public String home(Model model) {
        log.info("홈 페이지 접속");

        model.addAttribute("totalPostCount", postService.totalPostCount());
        model.addAttribute("superCategory", categoryService.superCategory());
        model.addAttribute("subCategory", categoryService.subCategory());
        return "home";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        log.info("로그인 페이지 이동");

        model.addAttribute("totalPostCount", postService.totalPostCount());
        model.addAttribute("superCategory", categoryService.superCategory());
        model.addAttribute("subCategory", categoryService.subCategory());
        return "login/loginForm";
    }

}
