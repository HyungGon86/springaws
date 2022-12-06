package study.springaws.global.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import study.springaws.domain.post.repository.PostRepository;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final PostRepository postRepository;

    @GetMapping("/")
    public String home(Model model) {
        log.info("홈 페이지 접속");

        model.addAttribute("popularPosts", postRepository.popularPosts());
        return "home";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        log.info("로그인 페이지 이동");
        return "login/loginForm";
    }

}
