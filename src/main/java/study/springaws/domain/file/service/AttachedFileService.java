package study.springaws.domain.file.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import study.springaws.domain.file.domain.AttachedFile;
import study.springaws.domain.file.dto.UploadFileDto;
import study.springaws.domain.file.repository.AttachedFileRepository;
import study.springaws.domain.post.domain.Post;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttachedFileService {

    private final AttachedFileRepository attachedFileRepository;

    @Value("${file.dir}")
    private String fileDir;

    private UploadFileDto fileSave(MultipartFile multipartFile) {

        AttachedFile file = AttachedFile.builder()
                .multipartFile(multipartFile)
                .fileDir(fileDir)
                .build();

        attachedFileRepository.save(file);

        return new UploadFileDto(file.getId(), file.getOriginalName(), file.getSystemName());
    }

    private void deleteFilesToDisk(List<Long> fileId) {
        List<AttachedFile> fileList = attachedFileRepository.findAllByIdInOrPostIsNull(fileId);

        fileList.forEach(AttachedFile::fileDelete);
    }

    @Transactional
    public List<UploadFileDto> fileSaveList(List<MultipartFile> multipartFiles) {

        List<UploadFileDto> uploadFileDtoList = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                uploadFileDtoList.add(fileSave(multipartFile));
            }
        }
        return uploadFileDtoList;
    }

    @Transactional
    public void fileUpdatePostId(Post post, List<Long> filesId) {
        attachedFileRepository.bulkFileUpdate(post, filesId);
    }

    @Transactional
    public void deleteFile(List<Long> fileId) {

        deleteFilesToDisk(fileId);

        attachedFileRepository.bulkFileDeleteByIdOrPostIsNull(fileId);
    }




}
