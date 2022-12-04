package study.springaws.domain.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.springaws.domain.file.domain.AttachedFile;
import study.springaws.domain.post.domain.Post;

import java.util.List;

public interface AttachedFileRepository extends JpaRepository<AttachedFile, Long>, AttachedFileRepositoryCustom {

    @Modifying
    @Query("update AttachedFile f set f.post = :post where f.id in :filesId")
    void bulkFileUpdate(@Param("post") Post post, @Param("filesId") List<Long> filesId);

    List<AttachedFile> findAllByIdInOrPostIsNull(List<Long> fileId);

    @Modifying
    @Query("delete from AttachedFile f where f.id in :fileId or f.post is null")
    void bulkFileDeleteByIdOrPostIsNull(@Param("fileId") List<Long> fileId);

}
