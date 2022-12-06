package study.springaws.domain.post.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PopularPostsDto {

    private Long id;
    private String thumbnailUrl;
    private String title;
    private LocalDateTime createdDate;

    public PopularPostsDto(Long id, String thumbnailUrl, String title, LocalDateTime createdDate) {
        this.id = id;
        this.thumbnailUrl = thumbnailUrl;
        this.title = title;
        this.createdDate = createdDate;
    }
}
