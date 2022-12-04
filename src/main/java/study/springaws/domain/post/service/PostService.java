package study.springaws.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.springaws.domain.category.domain.Category;
import study.springaws.domain.category.repository.CategoryRepository;
import study.springaws.domain.category.service.CategoryService;
import study.springaws.domain.file.domain.AttachedFile;
import study.springaws.domain.file.dto.EditFileDto;
import study.springaws.domain.file.service.AttachedFileService;
import study.springaws.domain.post.domain.Post;
import study.springaws.domain.post.dto.*;
import study.springaws.domain.post.repository.PostRepository;
import study.springaws.domain.user.domain.User;
import study.springaws.domain.user.repository.UserRepository;

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
    public Post postSave(PostForm postForm, Long userId) {

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
        return post;
    }

    public Long totalPostCount() {

        return postRepository.countByCategoryIn(categoryService.parentIsNotNull());
    }

    public Page<PostListDto> postListByCategory(String name, Pageable pageable) {
        Category category = categoryRepository.findByName(name);
        if (category == null) {
            return postRepository.postListByCategory(null, pageable);
        }

        if (category.getParent() != null) {
            return postRepository.postListByCategory(category, pageable);
        } else {
            List<Category> categoryList = categoryRepository.findByParent(category);
            return postRepository.postListBySuperCategory(categoryList, pageable);
        }
    }

    @Transactional
    public PostViewDto postViewDto(Long postId) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글에 대한 요청입니다."));
        post.increaseHits();

        return new PostViewDto(post);
    }

    @Transactional
    public void postDelete(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글에 대한 요청입니다."));

        List<Long> fileIdList = post.getFiles().stream()
                .map(AttachedFile::getId)
                .collect(Collectors.toList());

        attachedFileService.deleteFile(fileIdList);
        postRepository.delete(post);
    }

    public EditPostDto editPostDto(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글에 대한 요청입니다."));

        EditPostDto editPostDto = EditPostDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .category(post.getCategory().getId())
                .postId(post.getId())
                .thumbnailUrl(post.getThumbnailUrl())
                .build();

        if (!post.getFiles().isEmpty()) {
            int sysNameIndex = editPostDto.getThumbnailUrl().lastIndexOf("\\");
            String systemName = editPostDto.getThumbnailUrl().substring(sysNameIndex + 1);

            List<EditFileDto> fileDtoList = post.getFiles().stream()
                    .map(EditFileDto::new)
                    .collect(Collectors.toList());

            editPostDto.setFileDtoList(fileDtoList);

            for (EditFileDto file : fileDtoList) {
                if (file.getSystemName().equals(systemName)) {
                    editPostDto.setThumbnailId(file.getFileId());
                }
            }
        }

        return editPostDto;
    }

    @Transactional
    public void modifyingPost(EditPostForm editPostForm) {

        Category category = categoryRepository.findById(editPostForm.getCategory()).orElseThrow(() -> new IllegalStateException("카테고리가 존재하지 않습니다."));
        Post post = postRepository.findById(editPostForm.getPostId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글에 대한 요청입니다."));

        post.editPost(editPostForm, category);

        attachedFileService.fileUpdatePostId(post, editPostForm.getFileList());

        attachedFileService.deleteFile(editPostForm.getFileDeleteList());
    }

    public List<PostByCategoryDto> postByCategoryDto(String name) {
        Category category = categoryRepository.findByName(name);

        return postRepository.postByCategory(category);
    }


}
