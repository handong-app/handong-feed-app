package app.handong.feed.service.impl;

import app.handong.feed.domain.TbSubject;
import app.handong.feed.domain.TbSubjectTag;
import app.handong.feed.dto.TbSubjectTagDto;
import app.handong.feed.exception.data.DuplicateEntityException;
import app.handong.feed.exception.data.NotFoundException;
import app.handong.feed.repository.TagRepository;
import app.handong.feed.repository.TbSubjectRepository;
import app.handong.feed.repository.TbSubjectTagRepository;
import app.handong.feed.service.ExternalSubjectService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ExternalSubjectServiceImpl implements ExternalSubjectService {

    private final TagRepository tagRepository;
    private final TbSubjectTagRepository tbSubjectTagRepository;
    private final TbSubjectRepository tbSubjectRepository;

    public ExternalSubjectServiceImpl(TagRepository tagRepository, TbSubjectTagRepository tbSubjectTagRepository, TbSubjectRepository tbSubjectRepository) {
        this.tagRepository = tagRepository;
        this.tbSubjectTagRepository = tbSubjectTagRepository;
        this.tbSubjectRepository = tbSubjectRepository;
    }

    public TbSubjectTagDto.CreateResDto createSubjectTag(TbSubjectTagDto.CreateReqDto dto) {
        // 해당 태그 정보가 존재하는지 먼저 확인
        tagRepository.findById(dto.getTagCode()).orElseThrow(() -> new NotFoundException(dto.getTagCode() + " tag not found!"));

        dto.setUpdatedByType("api");
        try {
            TbSubjectTag saved = tbSubjectTagRepository.save(dto.toEntity());
            return saved.toCreateResDto();
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEntityException("duplicate tag code: " + dto.getTagCode());
        }
    }

    @Override
    public TbSubjectTagDto.CreateResDto getLastSubjectAssign(String updated_by) {
        TbSubjectTagDto.CreateResDto result = tbSubjectTagRepository.findTopByUpdatedByOrderByCreatedAt(updated_by).orElseThrow(() -> new NotFoundException("Not Found")).toCreateResDto();
        TbSubject tbSubject = tbSubjectRepository.findById(Long.valueOf(result.getTbSubjectId() + "")).orElseThrow(() -> new NotFoundException("Not Found" + result.getTbSubjectId()));
        result.setLastSentAt(tbSubject.getLastSentAt());
        result.setLastSentChatId(tbSubject.getLastSentChatId());
        return result;
    }
}
