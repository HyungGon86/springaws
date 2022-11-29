package study.springaws.domain.post.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import study.springaws.domain.category.service.CategoryService;
import study.springaws.domain.post.domain.Post;
import study.springaws.domain.post.dto.PostForm;
import study.springaws.domain.post.dto.PostListDto;
import study.springaws.domain.post.service.PostService;
import study.springaws.global.oauth.PrincipalDetails;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final CategoryService categoryService;

    @GetMapping("/write")
    public String postWrite(Model model) {
        model.addAttribute("postForm", new PostForm());
        model.addAttribute("totalPostCount", postService.totalPostCount());
        model.addAttribute("superCategory", categoryService.superCategory());
        model.addAttribute("subCategory", categoryService.subCategory());
        return "post/post-form";
    }

    @PostMapping("/write")
    public String write(@Validated @ModelAttribute PostForm postForm, Errors errors,
                        @AuthenticationPrincipal PrincipalDetails principalDetails) {

        System.out.println("errors = " + errors);
        System.out.println("postForm = " + postForm);
        System.out.println("principalDetails = " + principalDetails.getUserId());

        if (errors.hasErrors()) {
            return "post/post-form";
        }

        for (int i = 0; i < 50; i++) {
            postService.postSave(postForm, principalDetails.getUserId());
        }
//        postService.postSave(postForm, principalDetails.getUserId());

        return "redirect:/";
    }

    @GetMapping("/list")
    public String postListByCategory(@RequestParam String category,
                                     @PageableDefault(size = 5) Pageable pageable,
                                     Model model) {

        System.out.println("pageable = " + pageable);
        Page<PostListDto> postListDto = postService.postListByCategory(category, pageable);

        System.out.println("postListDto.getContent() = " + postListDto.getContent());

        int start = (int) (Math.floor(postListDto.getNumber() / postListDto.getSize()) * postListDto.getSize());
        int end = Math.min(postListDto.getTotalPages(), postListDto.getSize() + start - 1);

        model.addAttribute("postList", postListDto);
        model.addAttribute("start", start);
        model.addAttribute("end", end);

        return "post/postList";
    }
}
