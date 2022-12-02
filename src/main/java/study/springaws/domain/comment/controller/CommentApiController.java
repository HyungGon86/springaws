package study.springaws.domain.comment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
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
    public String commentWrite(@Validated @RequestBody CommentForm commentForm,
                               @AuthenticationPrincipal PrincipalDetails principalDetails) {

        System.out.println("commentForm = " + commentForm);

        if (principalDetails == null) {
            return "false";
        } else {
            commentService.commentSave(commentForm, principalDetails.getUserId());
        }

        return "true";
    }

    @GetMapping("/list/{postId}")
    public List<CommentListDto> commentList(@PathVariable Long postId) {

        System.out.println("postId = " + postId);

        return commentService.convertNestedStructure(postId);
    }
}
