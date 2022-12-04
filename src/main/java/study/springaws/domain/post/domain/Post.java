package study.springaws.domain.post.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import study.springaws.domain.category.domain.Category;
import study.springaws.domain.comment.domain.Comment;
import study.springaws.domain.file.domain.AttachedFile;
import study.springaws.domain.post.dto.EditPostForm;
import study.springaws.domain.user.domain.User;
import study.springaws.global.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ColumnDefault("0")
    private Long hit;

    private String thumbnailUrl;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<AttachedFile> files = new ArrayList<>();

    @Builder
    public Post(User user, Category category, String title, String content, String thumbnailUrl) {

        if (user != null) {
            setUser(user);
        }

        if (category != null) {
            changeCategory(category);
        }

        this.title = title;
        this.content = content;
        this.thumbnailUrl = thumbnailUrl;
    }

    /* 연관관계 메서드 */
    private void setUser(User user) {
        this.user = user;
        user.getPosts().add(this);
    }

    public void changeCategory(Category category) {
        this.category = category;
        category.getPosts().add(this);
    }

    /* 비즈니스 로직 */
    // 조회 수 증가
    public void increaseHits() {
        this.hit++;
    }

    public void editPost(EditPostForm editPostForm, Category category) {
        if (category != null) {
            changeCategory(category);
        }
        this.title = editPostForm.getTitle();
        this.content = editPostForm.getContent();
        this.thumbnailUrl = editPostForm.getThumbnailUrl();
    }
}
