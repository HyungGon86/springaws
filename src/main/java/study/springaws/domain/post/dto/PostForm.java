package study.springaws.domain.post.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PostForm {

    @NotEmpty
    private String category;

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    private String thumbnailUrl;
}
