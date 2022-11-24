package study.springaws.domain.comment.repository;

import study.springaws.domain.comment.domain.Comment;

import java.util.List;

public interface CommentRepositoryCustom {

    List<Comment> findCommentByPostId(Long postId);
}
