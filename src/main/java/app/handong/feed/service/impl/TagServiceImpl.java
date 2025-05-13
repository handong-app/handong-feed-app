package app.handong.feed.service.impl;

import app.handong.feed.domain.Tag;
import app.handong.feed.domain.TbTagReport;
import app.handong.feed.dto.TagDto;
import app.handong.feed.exception.data.NotFoundException;
import app.handong.feed.repository.TagRepository;
import app.handong.feed.repository.TbTagReportRepository;
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
    private final TbTagReportRepository tbTagReportRepository;

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

    @Override
    @Transactional
    public boolean reportTag(TagDto.ReportReqDto dto, String reportedBy) {
        try {
            tbTagReportRepository.save(TbTagReport.of(dto.getSubjectId(), dto.getMessage(), reportedBy));
            return true;
        } catch (Exception e) {
            log.error("태그 신고 저장 중 오류 발생: {}", e.getMessage(), e);
            return false;
        }
    }
}