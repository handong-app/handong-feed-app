package app.handong.feed.service;

import app.handong.feed.dto.TbSubjectTagDto;
import org.springframework.stereotype.Service;

@Service
public interface ExternalSubjectService {

    TbSubjectTagDto.CreateResDto createSubjectTag(TbSubjectTagDto.CreateReqDto dto);
    TbSubjectTagDto.CreateResDto getLastSubjectAssign(String updated_by);
}
