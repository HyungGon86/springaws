package study.springaws.domain.post.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostListDto {

    private Long id;
    private LocalDateTime createdDate;
    private String title;
    private String content;
    private String thumbnailUrl;

    public PostListDto() {
    }

    public PostListDto(Long id, LocalDateTime createdDate, String title, String content, String thumbnailUrl) {
        this.id = id;
        this.createdDate = createdDate;
        this.title = title;
        this.content = content;
        this.thumbnailUrl = thumbnailUrl;
    }
}
