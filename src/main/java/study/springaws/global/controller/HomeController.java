package study.springaws.global.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import study.springaws.domain.category.service.CategoryService;
import study.springaws.domain.post.service.PostService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        log.info("홈 페이지 접속");
        return "home";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        log.info("로그인 페이지 이동");
        return "login/loginForm";
    }

}
