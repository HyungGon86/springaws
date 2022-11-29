package study.springaws.domain.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import study.springaws.domain.category.domain.Category;
import study.springaws.domain.post.dto.PostListDto;

import java.util.List;

public interface PostRepositoryCustom {

    Page<PostListDto> postListByCategory(Category category, Pageable pageable);
}
