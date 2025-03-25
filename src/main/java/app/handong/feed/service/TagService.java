package app.handong.feed.service;

import app.handong.feed.dto.TagDto;
import org.springframework.stereotype.Service;

@Service
public interface TagService {
    TagDto.CreateResDto createTag(TagDto.CreateReqDto dto);
    TagDto.DeleteResDto deleteTag(int id);
}
