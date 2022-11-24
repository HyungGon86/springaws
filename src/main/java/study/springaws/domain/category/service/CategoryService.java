package study.springaws.domain.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.springaws.domain.category.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
}
