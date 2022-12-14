package study.springaws.domain.category.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import study.springaws.domain.category.dto.CategoryEditForm;
import study.springaws.domain.post.domain.Post;
import study.springaws.domain.post.domain.QPost;

import java.util.List;

import static study.springaws.domain.category.domain.QCategory.category;
import static study.springaws.domain.post.domain.QPost.*;

@RequiredArgsConstructor
public class CategoryRepositoryCustomImpl implements CategoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CategoryEditForm> superCategory() {
        return queryFactory.select(Projections.constructor(
                        CategoryEditForm.class,
                        category.id,
                        category.parent.id.as("parentId"),
                        category.name,
                        category.tier
                ))
                .from(category)
                .where(category.parent.id.isNull())
                .orderBy(category.tier.asc())
                .fetch();
    }

    @Override
    public List<CategoryEditForm> subCategory() {
        return queryFactory.select(Projections.constructor(
                        CategoryEditForm.class,
                        category.id,
                        category.parent.id.as("parentId"),
                        category.name,
                        category.tier,
                        post.countDistinct().as("count")
                ))
                .from(category)
                .leftJoin(category.posts, post)
                .groupBy(category.id)
                .where(category.parent.id.isNotNull())
                .orderBy(
                        category.parent.id.asc(),
                        category.tier.asc())
                .fetch();
    }

}
