package study.springaws.domain.post.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.springaws.domain.category.domain.Category;
import study.springaws.domain.category.dto.CategoryEditForm;
import study.springaws.domain.category.repository.CategoryRepository;
import study.springaws.domain.category.service.CategoryService;
import study.springaws.domain.file.repository.AttachedFileRepository;
import study.springaws.domain.file.service.AttachedFileService;
import study.springaws.domain.post.domain.Post;
import study.springaws.domain.post.dto.PostForm;
import study.springaws.domain.post.dto.PostListDto;
import study.springaws.domain.post.repository.PostRepository;
import study.springaws.domain.user.domain.User;
import study.springaws.domain.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final AttachedFileService attachedFileService;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Transactional
    public void postSave(PostForm postForm, Long userId) {

        Category category = categoryRepository.findById(postForm.getCategory()).orElseThrow(() -> new IllegalStateException("카테고리가 존재하지 않습니다."));
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));

        Post post = Post.builder()
                .title(postForm.getTitle())
                .content(postForm.getContent())
                .category(category)
                .user(user)
                .thumbnailUrl(postForm.getThumbnailUrl())
                .build();

        postRepository.save(post);

        attachedFileService.fileUpdatePostId(post, postForm.getFileList());

        attachedFileService.deleteFile(postForm.getFileDeleteList());
    }

    public Long totalPostCount() {

        return postRepository.countByCategoryIn(categoryService.parentIsNotNull());
    }

    public Page<PostListDto> postListByCategory(String name, Pageable pageable) {
        Category category = categoryRepository.findByName(name);

        return postRepository.postListByCategory(category, pageable);
    }




}
