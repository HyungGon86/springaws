package study.springaws.domain.post.dto;

import lombok.Builder;
import lombok.Data;
import study.springaws.domain.post.domain.Post;

import java.time.LocalDateTime;

@Data
public class PostViewDto {

    private Long postId;
    private Long userId;
    private String title;
    private String content;
    private String thumbnailUrl;
    private String category;
    private LocalDateTime createdDate;

    public PostViewDto(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.thumbnailUrl = post.getThumbnailUrl();
        this.category = post.getCategory().getName();
        this.createdDate = post.getCreatedDate();
    }
}
