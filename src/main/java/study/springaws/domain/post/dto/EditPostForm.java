package study.springaws.domain.post.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
public class EditPostForm {

    private Long postId;
    private Long category;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private String thumbnailUrl;

    private List<Long> fileList;

    private List<Long> fileDeleteList = new ArrayList<>();
}
