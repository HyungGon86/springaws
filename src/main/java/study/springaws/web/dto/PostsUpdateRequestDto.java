package study.springaws.web.dto;

import lombok.Data;

@Data
public class PostsUpdateRequestDto {

    private String title;
    private String content;

    public PostsUpdateRequestDto() {
    }

    public PostsUpdateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
