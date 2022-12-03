package study.springaws.global.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import study.springaws.domain.category.service.CategoryService;
import study.springaws.domain.comment.repository.CommentRepository;
import study.springaws.domain.post.service.PostService;

@ControllerAdvice
@RequiredArgsConstructor
public class ControllerHandler {

    private final PostService postService;
    private final CategoryService categoryService;
    private final CommentRepository commentRepository;

    @ModelAttribute
    public void commonModel(Model model) {
        model.addAttribute("totalPostCount", postService.totalPostCount());
        model.addAttribute("superCategory", categoryService.superCategory());
        model.addAttribute("subCategory", categoryService.subCategory());
        model.addAttribute("commentList", commentRepository.sidebarCommentList());
    }
}
