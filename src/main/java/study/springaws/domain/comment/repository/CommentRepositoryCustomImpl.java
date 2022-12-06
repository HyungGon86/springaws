package study.springaws.domain.comment.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import study.springaws.domain.comment.dto.CommentListDto;
import study.springaws.domain.comment.dto.SidebarCommentDto;

import java.util.List;

import static study.springaws.domain.comment.domain.QComment.comment;
import static study.springaws.domain.user.domain.QUser.*;

@RequiredArgsConstructor
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CommentListDto> findCommentByPostId(Long postId) {
        return queryFactory.select(
                        Projections.constructor(
                                CommentListDto.class,
                                comment.id.as("commentId"),
                                comment.parent.id.as("parentId"),
                                comment.user.id.as("userId"),
                                comment.content,
                                user.nickname,
                                user.imgUrl,
                                comment.secret,
                                comment.deleteStatus,
                                comment.createdDate
                        ))
                .from(comment)
                .innerJoin(comment.user, user)
                .leftJoin(comment.parent)
                .where(comment.post.id.eq(postId))
                .orderBy(
                        comment.parent.id.asc().nullsFirst(),
                        comment.createdDate.asc()
                ).fetch();
    }

    @Override
    @Cacheable(value = "layoutRecentCommentCaching", key = "0")
    public List<SidebarCommentDto> sidebarCommentList() {
        return queryFactory.select(
                        Projections.constructor(
                                SidebarCommentDto.class,
                                comment.post.id.as("postId"),
                                comment.content,
                                comment.secret
                        ))
                .from(comment)
                .orderBy(comment.createdDate.desc())
                .fetch();
    }
}
