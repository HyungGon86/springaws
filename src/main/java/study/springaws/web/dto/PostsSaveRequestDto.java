package study.springaws.web.dto;

import lombok.Data;

@Data
public class PostsSaveRequestDto {

    private String title;
    private String content;
    private String author;

    public PostsSaveRequestDto() {
    }

    public PostsSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
