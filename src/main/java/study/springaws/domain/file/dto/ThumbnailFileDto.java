package study.springaws.domain.file.dto;

import lombok.Data;

import java.nio.file.Paths;
import java.time.LocalDate;

@Data
public class ThumbnailFileDto {

    private Long fileId;
    private String originalName;
    private String filePath;

    public ThumbnailFileDto(Long fileId, String originalName, String systemName) {
        this.fileId = fileId;
        this.originalName = originalName;
        this.filePath = Paths.get("/thumbnail/", LocalDate.now().toString(), systemName).toString();
    }
}
