package study.springaws.domain.category.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CategoryEditForm {

    private Long id;
    private Long parentId;

    @NotBlank
    private String name;

    @NotBlank
    private int tier;

    public CategoryEditForm() {
    }

    public CategoryEditForm(Long id, Long parentId, String name, int tier) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.tier = tier;
    }
}
