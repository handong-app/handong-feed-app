package app.handong.feed.service.impl;

import app.handong.feed.domain.TbSubjectTag;
import app.handong.feed.dto.TbSubjectTagDto;
import app.handong.feed.exception.data.NotFoundException;
import app.handong.feed.repository.TagRepository;
import app.handong.feed.repository.TbSubjectTagRepository;
import app.handong.feed.service.TbSubjectTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TbSubjectTagServiceImpl implements TbSubjectTagService {
    private final TbSubjectTagRepository subjectTagRepository;
    private final TagRepository tagRepository;

    public TbSubjectTagDto.CreateResDto createSubjectTag(TbSubjectTagDto.CreateReqDto dto) {
        // 해당 태그 정보가 존재하는지 먼저 확인
        tagRepository.findById(dto.getTagCode()).orElseThrow(() -> new NotFoundException(dto.getTagCode() + " tag not found!"));

        TbSubjectTag saved = subjectTagRepository.save(dto.toEntity());
        return new TbSubjectTagDto.CreateResDto(
                saved.getId(),
                saved.getTbSubjectId(),
                saved.getTagCode(),
                saved.getConfidentValue(),
                saved.getForDate(),
                saved.getCreatedAt()
        );
    }

    public TbSubjectTagDto.DeleteResDto deleteSubjectTag(int id) {
        subjectTagRepository.deleteById(id);
        return new TbSubjectTagDto.DeleteResDto(id, "SubjectTag deleted successfully");
    }

    public TbSubjectTagDto.DeleteResDto deleteSubjectTag(TbSubjectTagDto.DeleteReqDto dto) {
        return deleteSubjectTag(
                subjectTagRepository.findByTbSubjectIdAndTagCode(dto.getTbSubjectId(), dto.getTagCode())
                        .orElseThrow(() -> new NotFoundException(
                                "SubjectTag not found with subjectId: " + dto.getTbSubjectId() + ", tagCode: " + dto.getTagCode()
                        ))
                        .getId()
        );
    }

    public List<TbSubjectTagDto.ReadResDto> readSubjectTags(int subjectId) {
        return subjectTagRepository.findByTbSubjectId(subjectId).stream().map(subjectTag ->
            new TbSubjectTagDto.ReadResDto(
                    subjectTag.getId(),
                    subjectTag.getTbSubjectId(),
                    subjectTag.getTagCode(),
                    subjectTag.getTag().toDto(),
                    subjectTag.getConfidentValue(),
                    subjectTag.getForDate(),
                    subjectTag.getUpdatedBy(),
                    subjectTag.getCreatedAt(),
                    subjectTag.getUpdatedAt()

            )
        ).toList();
    }
}
