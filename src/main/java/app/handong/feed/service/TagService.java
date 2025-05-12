package app.handong.feed.service;

import app.handong.feed.dto.TagDto;

import java.util.List;


public interface TagService {
    TagDto.ReadResDto readTag(String code);

    List<TagDto.ReadResDto> readAllTags();

    boolean reportTag(TagDto.ReportReqDto dto, String reportedBy);
}
