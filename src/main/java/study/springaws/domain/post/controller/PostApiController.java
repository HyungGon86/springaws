package study.springaws.domain.post.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import study.springaws.domain.post.service.PostService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/post")
public class PostApiController {

    private final PostService postService;

    @DeleteMapping("/delete/{postId}")
    public void deletePost(@PathVariable Long postId) {
        postService.postDelete(postId);
    }

}
