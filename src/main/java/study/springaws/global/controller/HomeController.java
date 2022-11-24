package study.springaws.global.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String home() {
        log.info("홈 페이지 접속");
        return "home";
    }

    @GetMapping("/login")
    public String loginForm() {
        log.info("로그인 페이지 이동");
        return "login/loginForm";
    }

}
