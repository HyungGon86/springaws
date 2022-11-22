package study.springaws.domain.file.dto;

import lombok.Data;

@Data
public class UploadFileDto {

    private Long fileId;
    private String originalName;
    private String filePath;

    public UploadFileDto(Long fileId, String originalName, String filePath) {
        this.fileId = fileId;
        this.originalName = originalName;
        this.filePath = filePath;
    }
}
