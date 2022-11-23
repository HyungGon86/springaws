package study.springaws.domain.post.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.springaws.domain.file.repository.AttachedFileRepository;
import study.springaws.domain.file.service.AttachedFileService;
import study.springaws.domain.post.domain.Post;
import study.springaws.domain.post.dto.PostForm;
import study.springaws.domain.post.repository.PostRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final AttachedFileService attachedFileService;

    @Transactional
    public void postSave(PostForm postForm) {

        Post post = Post.builder()
                .title(postForm.getTitle())
                .content(postForm.getContent())
                .build();

        postRepository.save(post);

        attachedFileService.fileUpdatePostId(post, postForm.getFileList());

        attachedFileService.deleteFile(postForm.getFileDeleteList());
    }




}
