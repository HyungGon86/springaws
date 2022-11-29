package study.springaws.domain.post.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import study.springaws.domain.category.domain.Category;
import study.springaws.domain.category.domain.QCategory;
import study.springaws.domain.post.domain.QPost;
import study.springaws.domain.post.dto.PostListDto;

import java.util.List;

import static study.springaws.domain.category.domain.QCategory.*;
import static study.springaws.domain.post.domain.QPost.*;

@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<PostListDto> postListByCategory(Category category, Pageable pageable) {
        List<PostListDto> content = queryFactory
                .select(Projections.constructor(
                        PostListDto.class,
                        post.id,
                        post.createdDate,
                        post.title,
                        post.content,
                        post.thumbnailUrl
                ))
                .from(post)
                .where(categoryEq(category))
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(post.count())
                .from(post)
                .where(categoryEq(category));

        System.out.println("content = " + content);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression categoryEq(Category category) {
        return category != null ? post.category.eq(category) : null;
    }
}
