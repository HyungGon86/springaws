package study.springaws.domain.comment.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentForm {

    private Long postId;
    private Long parentId;

    @NotBlank
    private String content;

    private boolean secret;

    public CommentForm() {
    }

    public CommentForm(Long postId, Long parentId, String content, boolean secret) {
        this.postId = postId;
        this.parentId = parentId;
        this.content = content;
        this.secret = secret;
    }
}
