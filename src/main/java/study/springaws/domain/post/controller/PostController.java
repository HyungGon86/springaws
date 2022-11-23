package study.springaws.domain.post.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import study.springaws.domain.post.domain.Post;
import study.springaws.domain.post.dto.PostForm;
import study.springaws.domain.post.service.PostService;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @GetMapping("/write")
    public String postWrite(Model model) {
        model.addAttribute("postForm", new PostForm());
        return "post/post-form";
    }

    @PostMapping("/write")
    public String write(@Validated @ModelAttribute PostForm postForm, Errors errors) {

        System.out.println("errors = " + errors);
        System.out.println("postForm = " + postForm);

        if (errors.hasErrors()) {
            return "post/post-form";
        }

        postService.postSave(postForm);

        return "redirect:/";
    }
}
