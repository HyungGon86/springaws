package study.springaws.domain.comment.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import study.springaws.domain.post.domain.Post;
import study.springaws.domain.user.domain.User;
import study.springaws.global.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent")
    private List<Comment> child = new ArrayList<>();

    private String content;

    private boolean secret;

    @ColumnDefault("0")
    private boolean deleteStatus;

    @Builder
    public Comment(User user, Post post, Comment parent, String content, boolean secret, boolean deleteStatus) {

        if (user != null) {
            setUser(user);
        }

        if (post != null) {
            setPost(post);
        }

        if (parent != null) {
            setParent(parent);
        }

        this.content = content;
        this.secret = secret;
        this.deleteStatus = deleteStatus;
    }

    /* 연관관계 메서드 */
    private void setUser(User user) {
        this.user = user;
        user.getComments().add(this);
    }

    private void setPost(Post post) {
        this.post = post;
        post.getComments().add(this);
    }

    private void setParent(Comment parent) {
        this.parent = parent;
        parent.getChild().add(this);
    }

    /* 비즈니스 메서드 */

}
