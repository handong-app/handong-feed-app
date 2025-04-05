package app.handong.feed.service;

import app.handong.feed.dto.TagDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagService {
    TagDto.ReadResDto readTag(String code);
    List<TagDto.ReadResDto> readAllTags();
}
