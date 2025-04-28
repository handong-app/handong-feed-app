package app.handong.feed.service;

import org.springframework.stereotype.Service;

@Service
public interface ExternalSubjectService {
    void updateIsTagAssignedTrue(Long subjectId);
}
