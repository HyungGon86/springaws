package study.springaws.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.springaws.domain.comment.domain.Comment;
import study.springaws.domain.comment.dto.CommentForm;
import study.springaws.domain.comment.dto.CommentListDto;
import study.springaws.domain.comment.repository.CommentRepository;
import study.springaws.domain.post.domain.Post;
import study.springaws.domain.post.repository.PostRepository;
import study.springaws.domain.user.domain.User;
import study.springaws.domain.user.repository.UserRepository;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public void commentSave(CommentForm commentForm, Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        Post post = postRepository.findById(commentForm.getPostId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 포스트입니다."));

        Comment comment = Comment.builder()
                .post(post)
                .user(user)
                .content(commentForm.getContent())
                .secret(commentForm.isSecret())
                .build();

        commentRepository.save(comment);
    }

    public List<CommentListDto> convertNestedStructure(Long postId) {

        List<CommentListDto> commentListDtoList = commentRepository.findCommentByPostId(postId);

        List<CommentListDto> result = new ArrayList<>();
        Map<Long, CommentListDto> map = new HashMap<>();

        commentListDtoList.forEach(list -> {
                    map.put(list.getCommentId(), list);
                    if (list.getParentId() != null) {
                        map.get(list.getParentId()).getChild().add(list);
                    } else {
                        result.add(list);
                    }
                });

        return result;
    }

}
