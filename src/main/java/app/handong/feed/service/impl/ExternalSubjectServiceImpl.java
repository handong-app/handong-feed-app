package app.handong.feed.service.impl;

import app.handong.feed.mapper.TbsubjectMapper;
import app.handong.feed.service.ExternalSubjectService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ExternalSubjectServiceImpl implements ExternalSubjectService {
    private final TbsubjectMapper tbsubjectMapper;

    public ExternalSubjectServiceImpl(TbsubjectMapper tbsubjectMapper) {
        this.tbsubjectMapper = tbsubjectMapper;
    }

    @Override
    @Transactional
    public void updateIsTagAssignedTrue(Long subjectId) {
        tbsubjectMapper.updateIsTagAssignedTrue(subjectId);
    }
}
