package study.springaws.domain.comment.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CommentListDto {

    private Long commentId;
    private Long parentId;
    private Long userId;
    private String content;
    private String nickname;
    private String imgUrl;
    private boolean secret;
    private boolean deleteStatus;
    private LocalDateTime createdDate;

    private List<CommentListDto> child = new ArrayList<>();

    public CommentListDto() {
    }

    public CommentListDto(Long commentId, Long parentId, Long userId, String content, String nickname, String imgUrl, boolean secret, boolean deleteStatus, LocalDateTime createdDate) {
        this.commentId = commentId;
        this.parentId = parentId;
        this.userId = userId;
        this.content = content;
        this.nickname = nickname;
        this.imgUrl = imgUrl;
        this.secret = secret;
        this.deleteStatus = deleteStatus;
        this.createdDate = createdDate;
    }
}
