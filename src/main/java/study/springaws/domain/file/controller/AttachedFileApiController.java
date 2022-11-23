package study.springaws.domain.file.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import study.springaws.domain.file.dto.UploadFileDto;
import study.springaws.domain.file.service.AttachedFileService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
@Slf4j
public class AttachedFileApiController {

    private final AttachedFileService fileService;

    @PostMapping("/upload")
    public List<UploadFileDto> saveFiles(@RequestParam List<MultipartFile> attachFiles) {
        return fileService.fileSaveList(attachFiles);
    }


}
