package app.handong.feed.repository;

import app.handong.feed.domain.TbSubjectTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TbSubjectTagRepository extends JpaRepository<TbSubjectTag, Integer> {
    // 특정 subjectId의 태그 목록 조회
    List<TbSubjectTag> findByTbSubjectId(int tbSubjectId);

    // subjectId + forDate 조건으로 조회
    List<TbSubjectTag> findByTbSubjectIdAndForDate(int tbSubjectId, LocalDate forDate);

    // subjectId, tagId 조합 으로 조회
    Optional<TbSubjectTag> findByTbSubjectIdAndTagCode(int tbSubjectId, String tagCode);

    // subjectId, tagId 조합 존재 여부 확인 (복합키 유니크 체크에 유용)
    boolean existsByTbSubjectIdAndTagCode(int tbSubjectId, String tagCode);
}