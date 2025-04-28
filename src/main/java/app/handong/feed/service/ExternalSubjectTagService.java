package app.handong.feed.service;

import app.handong.feed.dto.TbSubjectTagDto;


public interface ExternalSubjectTagService {
    TbSubjectTagDto.CreateResDto createSubjectTag(TbSubjectTagDto.CreateReqDto dto);
    TbSubjectTagDto.GetLatestForDateResDto readLatestForDate();
}
