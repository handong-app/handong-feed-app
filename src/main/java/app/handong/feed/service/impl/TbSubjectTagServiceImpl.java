package app.handong.feed.service.impl;

import app.handong.feed.domain.TbSubjectTag;
import app.handong.feed.dto.TbSubjectTagDto;
import app.handong.feed.repository.TbSubjectTagRepository;
import app.handong.feed.service.TbSubjectTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TbSubjectTagServiceImpl implements TbSubjectTagService {
    private final TbSubjectTagRepository subjectTagRepository;
    public TbSubjectTagDto.CreateResDto createSubjectTag(TbSubjectTagDto.CreateReqDto dto) {
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
}
