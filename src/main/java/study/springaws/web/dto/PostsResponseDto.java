package study.springaws.web.dto;

import lombok.Data;
import study.springaws.domain.posts.Post;

@Data
public class PostsResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;

    public PostsResponseDto() {
    }

    public PostsResponseDto(Post entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}
