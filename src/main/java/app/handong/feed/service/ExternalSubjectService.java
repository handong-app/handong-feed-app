package app.handong.feed.service;

import app.handong.feed.dto.TbSubjectTagDto;
import org.springframework.stereotype.Service;

@Service
public interface ExternalSubjectService {
    public TbSubjectTagDto.CreateResDto createSubjectTag(TbSubjectTagDto.CreateReqDto dto);
    public TbSubjectTagDto.GetLatestForDateResDto readLatestForDate();
}
