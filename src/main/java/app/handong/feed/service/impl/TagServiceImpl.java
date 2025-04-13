package app.handong.feed.service.impl;

import app.handong.feed.domain.Tag;
import app.handong.feed.dto.TagDto;
import app.handong.feed.exception.data.NotFoundException;
import app.handong.feed.repository.TagRepository;
import app.handong.feed.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    @Transactional(readOnly = true)
    public TagDto.ReadResDto readTag(String code) {
        Tag tag = tagRepository.findById(code)
                .orElseThrow(() -> new NotFoundException("Tag not found with code: " + code));

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
}