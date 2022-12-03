package study.springaws.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import study.springaws.domain.comment.dto.CommentForm;
import study.springaws.domain.comment.dto.CommentListDto;
import study.springaws.domain.comment.service.CommentService;
import study.springaws.global.oauth.PrincipalDetails;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/comment")
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/write")
    public void commentWrite(@Validated @RequestBody CommentForm commentForm,
                               @AuthenticationPrincipal PrincipalDetails principalDetails) {

        commentService.commentSave(commentForm, principalDetails.getUserId());
    }

    @GetMapping("/list/{postId}")
    public List<CommentListDto> commentList(@PathVariable Long postId) {

        return commentService.convertNestedStructure(postId);
    }

    @DeleteMapping("/delete/{commentId}")
    public void commentDelete(@PathVariable Long commentId) {

        commentService.commentDelete(commentId);
    }
}
