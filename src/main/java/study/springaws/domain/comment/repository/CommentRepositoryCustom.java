package study.springaws.domain.comment.repository;

import study.springaws.domain.comment.dto.CommentListDto;
import study.springaws.domain.comment.dto.SidebarCommentDto;

import java.util.List;

public interface CommentRepositoryCustom {

    List<CommentListDto> findCommentByPostId(Long postId);

    List<SidebarCommentDto> sidebarCommentList();
}
