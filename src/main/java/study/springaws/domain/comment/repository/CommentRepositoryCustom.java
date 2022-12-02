package study.springaws.domain.comment.repository;

import study.springaws.domain.comment.dto.CommentListDto;

import java.util.List;

public interface CommentRepositoryCustom {

    List<CommentListDto> findCommentByPostId(Long postId);
}
