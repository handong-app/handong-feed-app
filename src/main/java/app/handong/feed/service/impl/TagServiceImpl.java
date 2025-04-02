package app.handong.feed.service.impl;

import app.handong.feed.domain.Tag;
import app.handong.feed.dto.TagDto;
import app.handong.feed.exception.data.DuplicateTagCodeException;
import app.handong.feed.exception.data.NoMatchingDataException;
import app.handong.feed.repository.TagRepository;
import app.handong.feed.service.TagService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public TagDto.CreateResDto createTag(TagDto.CreateReqDto dto) {
        if (tagRepository.existsById(dto.getCode())) {
            throw new DuplicateTagCodeException(dto.getCode());
        }

        return TagDto.CreateResDto.fromEntity(tagRepository.save(dto.toEntity()));
    }

    public List<TagDto.CreateResDto> createTags(List<TagDto.CreateReqDto> requestList) {
        List<Tag> tags = requestList.stream()
                .map(TagDto.CreateReqDto::toEntity)
                .toList();

        for (Tag tag : tags) {
            if (tagRepository.existsById(tag.getCode())) {
                throw new DuplicateTagCodeException(tag.getCode());
            }
        }

        List<Tag> savedTags = tagRepository.saveAll(tags);
        return savedTags.stream()
                .map(TagDto.CreateResDto::fromEntity)
                .toList();
    }


    @Override
    @Transactional(readOnly = true)
    public TagDto.ReadResDto readTag(String code) {
        Tag tag = tagRepository.findById(code)
                .orElseThrow(() -> new NoMatchingDataException("Tag not found with code: " + code));

        return new TagDto.ReadResDto(
                tag.getCode(),
                tag.getLabel(),
                tag.getUserDesc(),
                tag.getLlmDesc(),
                tag.getColorHex(),
                tag.getPriorityWeight(),
                tag.getCreatedAt(),
                tag.getUpdatedAt()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagDto.ReadResDto> readAllTags() {
        return tagRepository.findAll().stream()
                .map(tag -> new TagDto.ReadResDto(
                        tag.getCode(),
                        tag.getLabel(),
                        tag.getUserDesc(),
                        tag.getLlmDesc(),
                        tag.getColorHex(),
                        tag.getPriorityWeight(),
                        tag.getCreatedAt(),
                        tag.getUpdatedAt()
                ))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TagDto.UpdateResDto updateTag(String code, TagDto.UpdateReqDto dto) {
        Tag tag = tagRepository.findById(code)
                .orElseThrow(() -> new NoMatchingDataException("Tag not found with code: " + code));

        // 필드 업데이트
        tag.setLabel(dto.getLabel());
        tag.setUserDesc(dto.getUserDesc());
        tag.setLlmDesc(dto.getLlmDesc());
        tag.setColorHex(dto.getColorHex());
        tag.setPriorityWeight(dto.getPriorityWeight());

        return new TagDto.UpdateResDto(
                tag.getCode(),
                tag.getLabel(),
                tag.getUserDesc(),
                tag.getLlmDesc(),
                tag.getColorHex(),
                tag.getPriorityWeight(),
                tag.getUpdatedAt()
        );
    }

    @Override
    public TagDto.DeleteResDto deleteTag(String code) {
        tagRepository.findById(code)
            .orElseThrow(() -> new NoMatchingDataException("Tag not found with code: " + code));

        tagRepository.deleteById(code);
        return new TagDto.DeleteResDto(code, "Tag deleted successfully");
    }
}