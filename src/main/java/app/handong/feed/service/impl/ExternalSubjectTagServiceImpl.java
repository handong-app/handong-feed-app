package app.handong.feed.service.impl;

import app.handong.feed.domain.TbSubjectTag;
import app.handong.feed.dto.TbSubjectTagDto;
import app.handong.feed.exception.data.DuplicateEntityException;
import app.handong.feed.exception.data.NotFoundException;
import app.handong.feed.mapper.TbSubjectTagMapper;
import app.handong.feed.repository.TagRepository;
import app.handong.feed.repository.TbSubjectTagRepository;
import app.handong.feed.service.ExternalSubjectTagService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ExternalSubjectTagServiceImpl implements ExternalSubjectTagService {

    private final TagRepository tagRepository;
    private final TbSubjectTagRepository tbSubjectTagRepository;
    private final TbSubjectTagMapper tbSubjectTagMapper;

    public ExternalSubjectTagServiceImpl(TagRepository tagRepository, TbSubjectTagRepository tbSubjectTagRepository, TbSubjectTagMapper tbSubjectTagMapper) {
        this.tagRepository = tagRepository;
        this.tbSubjectTagRepository = tbSubjectTagRepository;
        this.tbSubjectTagMapper = tbSubjectTagMapper;
    }

    public TbSubjectTagDto.CreateResDto createSubjectTag(TbSubjectTagDto.CreateReqDto dto) {
        // 해당 태그 정보가 존재하는지 먼저 확인
        tagRepository.findById(dto.getTagCode()).orElseThrow(() -> new NotFoundException(dto.getTagCode() + " tag not found!"));

        dto.setUpdatedByType("api");
        try {
            TbSubjectTag saved = tbSubjectTagRepository.save(dto.toEntity());
            return new TbSubjectTagDto.CreateResDto(
                    saved.getId(),
                    saved.getTbSubjectId(),
                    saved.getTagCode(),
                    saved.getConfidentValue(),
                    saved.getForDate(),
                    saved.getCreatedAt()
            );
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEntityException("duplicate tag code: " + dto.getTagCode());
        }
    }

    @Override
    public TbSubjectTagDto.GetLatestForDateResDto readLatestForDate(){
        return tbSubjectTagMapper.getLatestForDate();
    }

}
