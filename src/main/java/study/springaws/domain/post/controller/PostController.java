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
import study.springaws.domain.post.dto.PostViewDto;
import study.springaws.domain.post.service.PostService;
import study.springaws.global.oauth.PrincipalDetails;

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
    public String write(@Validated @ModelAttribute PostForm postForm, Errors errors,
                        @AuthenticationPrincipal PrincipalDetails principalDetails) {

        System.out.println("errors = " + errors);

        if (errors.hasErrors()) {
            return "post/post-form";
        }

        postService.postSave(postForm, principalDetails.getUserId());
        return "redirect:/";
    }

    @GetMapping("/list")
    public String postListByCategory(@RequestParam String category,
                                     @PageableDefault(size = 5) Pageable pageable,
                                     Model model) {

        Page<PostListDto> postListDto = postService.postListByCategory(category, pageable);
        int totalPages = postListDto.getTotalPages() - 1;

        int start = (int) (Math.floor(postListDto.getNumber() / postListDto.getSize()) * postListDto.getSize());
        int end = Math.min(totalPages == -1 ? 0 : totalPages, postListDto.getSize() + start - 1);

        model.addAttribute("postList", postListDto);
        model.addAttribute("start", start);
        model.addAttribute("end", end);
        return "post/postList";
    }

    @GetMapping("/view")
    public String postView(@RequestParam Long postId,
                           @AuthenticationPrincipal PrincipalDetails principalDetails,
                           Model model) {

        PostViewDto postViewDto = postService.postViewDto(postId);

        if (principalDetails != null) {
            postViewDto.setUserId(principalDetails.getUserId());
            model.addAttribute("picUrl", principalDetails.getUserPicUrl());
        }

        model.addAttribute("post", postViewDto);
        return "post/postView";
    }
}
