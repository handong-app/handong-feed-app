package app.handong.feed.service.impl;

import app.handong.feed.domain.Tag;
import app.handong.feed.dto.TagDto;
import app.handong.feed.repository.TagRepository;
import app.handong.feed.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    public TagDto.CreateResDto createTag(TagDto.CreateReqDto dto) {
        Tag saved = tagRepository.save(dto.toEntity());

        return new TagDto.CreateResDto(
                saved.getId(),
                saved.getName(),
                saved.getNameKor(),
                saved.getDesc(),
                saved.getColor(),
                saved.getCreatedAt()
        );
    }

    public TagDto.DeleteResDto deleteTag(int id) {
        tagRepository.deleteById(id);
        return new TagDto.DeleteResDto(id, "Tag deleted successfully");
    }
}
