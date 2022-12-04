package study.springaws.domain.file.dto;

import lombok.Data;
import study.springaws.domain.file.domain.AttachedFile;

@Data
public class EditFileDto {

    private Long fileId;
    private String filePath;
    private String originalName;
    private String systemName;

    public EditFileDto(AttachedFile file) {
        this.fileId = file.getId();
        this.filePath = file.getFilePath();
        this.originalName = file.getOriginalName();
        this.systemName = file.getSystemName();
    }
}
