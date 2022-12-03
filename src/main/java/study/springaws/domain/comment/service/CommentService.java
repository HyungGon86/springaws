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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    /*
    *  - 댓글 저장
    * */
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

        if (commentForm.getParentId() != null) {
            Comment parent = commentRepository.findById(commentForm.getParentId()).orElseThrow(() -> new IllegalArgumentException("부모댓글이 존재하지 않습니다."));
            comment.setParent(parent);
        }
        commentRepository.save(comment);
    }

    /*
    *  - 계층형 댓글을 가져와서 스트림으로 정렬
    * */
    public List<CommentListDto> convertNestedStructure(Long postId) {

        List<CommentListDto> commentListDtoList = commentRepository.findCommentByPostId(postId);

        List<CommentListDto> result = new ArrayList<>();
        Map<Long, CommentListDto> map = new HashMap<>();

        commentListDtoList.forEach(obj -> {
            map.put(obj.getCommentId(), obj);
            if (obj.getParentId() != null) {
                map.get(obj.getParentId()).getChild().add(obj);
            } else {
                result.add(obj);
            }
        });

        return result;
    }

    /*
    *  - 댓글 삭제
    * */
    @Transactional
    public void commentDelete(Long commentId) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글에 대한 요청입니다."));

        if (comment.getParent() != null || comment.getChild().isEmpty()) {
            commentRepository.delete(comment);

            Optional<Comment> optionalComment = Optional.of(comment);

            Integer size = optionalComment
                    .map(Comment::getParent)
                    .map(Comment::getChild)
                    .map(List::size)
                    .orElse(0);

            if (size == 1 && comment.getParent().isDeleteStatus()) {
                commentRepository.delete(comment.getParent());
            }
        } else {
            comment.changeDeleteStatus(true);
        }
    }

}
