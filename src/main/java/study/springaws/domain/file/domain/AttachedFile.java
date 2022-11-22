package study.springaws.domain.file.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import study.springaws.domain.post.domain.Post;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttachedFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String originalName;

    private String systemName;

    private String filePath;

    @Builder
    private AttachedFile(String fileDir, MultipartFile multipartFile) {

        this.originalName = Objects.requireNonNull(multipartFile.getOriginalFilename());
        convertToSystemName(originalName);
        setFilePath(fileDir, systemName);

        try {
            multipartFile.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /* 연관관계 메서드 */
    private void setPost(Post post) {
        this.post = post;
        post.getFiles().add(this);
    }

    /* ==비즈니스 메서드== */
    // 해당 파일의 위치를 가르키는 풀 경로
    private void setFilePath(String fileDir, String systemName) {
        String nowDate = LocalDate.now().toString();
        File file = new File(fileDir + nowDate);
        if (!file.exists()) {
            file.mkdirs();
        }
        this.filePath = file + File.separator + systemName;
    }

    // 원본 파일명을 저장하는 랜덤 문자열로 변환
    private void convertToSystemName(String originalName) {
        String uuid = UUID.randomUUID().toString();
        int pos = originalName.lastIndexOf(".");
        String ext = originalName.substring(pos + 1);
        this.systemName = uuid + "." + ext;
    }

    // 실제 존재하는 파일 삭제
    public void fileDelete() {

        Path path = FileSystems.getDefault().getPath(this.filePath);

        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
