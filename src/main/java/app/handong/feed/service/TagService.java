package app.handong.feed.service;

import app.handong.feed.dto.TagDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagService {
    TagDto.CreateResDto createTag(TagDto.CreateReqDto dto);
    List<TagDto.CreateResDto> createTags(List<TagDto.CreateReqDto> requestList);
    TagDto.ReadResDto readTag(String code);
    List<TagDto.ReadResDto> readAllTags();
    TagDto.UpdateResDto updateTag(String code, TagDto.UpdateReqDto dto);
    TagDto.DeleteResDto deleteTag(String code);
}
