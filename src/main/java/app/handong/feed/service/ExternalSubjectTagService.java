package app.handong.feed.service;

import app.handong.feed.dto.TbSubjectTagDto;
import org.springframework.stereotype.Service;

@Service
public interface ExternalSubjectTagService {
    TbSubjectTagDto.CreateResDto createSubjectTag(TbSubjectTagDto.CreateReqDto dto);
    TbSubjectTagDto.GetLatestForDateResDto readLatestForDate();
}
