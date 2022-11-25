package study.springaws.domain.category.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.springaws.domain.post.domain.Post;
import study.springaws.global.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    private String name;

    private int tier;

    @OneToMany(mappedBy = "category")
    private List<Post> posts = new ArrayList<>();

    @Builder
    public Category(Category parent, String name, int tier) {
        if (parent != null) {
            setParent(parent);
        }
        this.name = name;
        this.tier = tier;
    }

    /* 연관관계 메서드 */
    private void setParent(Category parent) {
        this.parent = parent;
        parent.getChild().add(this);
    }

    /* 비즈니스 메서드 */
    public void changeName(String name) {
        this.name = name;
    }
}
