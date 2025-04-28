package app.handong.feed.service.impl;

import app.handong.feed.exception.data.NotFoundException;
import app.handong.feed.mapper.TbsubjectMapper;
import app.handong.feed.service.ExternalSubjectService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExternalSubjectServiceImpl implements ExternalSubjectService {
    private final TbsubjectMapper tbsubjectMapper;

    /**
     * 주어진 주제(subject)의 태그 할당 상태를 완료(true)로 업데이트합니다.
     *
     * @param subjectId 업데이트할 주제의 ID
     */
    @Override
    @Transactional
    public void updateIsTagAssignedTrue(Long subjectId) {
        int updatedRows = tbsubjectMapper.updateIsTagAssignedTrue(subjectId);
        // 영향을 받은 Row 가 0개라면 존재하지 않는 Subject에 대한 요청임.
        if (updatedRows == 0) {
            throw new NotFoundException("Subject not found with ID: " + subjectId);
        }
    }
}
