package study.springaws.domain.post.dto;

import lombok.Data;

@Data
public class PostByCategoryDto {

    private Long id;
    private String title;

    public PostByCategoryDto(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}
