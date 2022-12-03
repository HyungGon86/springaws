package study.springaws.domain.comment.dto;

import lombok.Data;

@Data
public class SidebarCommentDto {

    private Long postId;
    private String content;
    private boolean secret;

    public SidebarCommentDto(Long postId, String content, boolean secret) {
        this.postId = postId;
        this.content = content;
        this.secret = secret;
    }
}
