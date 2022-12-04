package study.springaws.domain.post.dto;

import lombok.Builder;
import lombok.Data;
import study.springaws.domain.file.dto.EditFileDto;

import java.util.ArrayList;
import java.util.List;

@Data
public class EditPostDto {

    private Long postId;
    private Long thumbnailId;
    private Long category;
    private String title;
    private String content;
    private String thumbnailUrl;

    private List<EditFileDto> fileDtoList = new ArrayList<>();

    @Builder
    private EditPostDto(Long postId, Long category, String title, String content, String thumbnailUrl) {
        this.postId = postId;
        this.category = category;
        this.title = title;
        this.content = content;
        this.thumbnailUrl = thumbnailUrl;
    }
}
