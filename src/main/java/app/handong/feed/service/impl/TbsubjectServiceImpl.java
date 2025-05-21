package app.handong.feed.service.impl;

import app.handong.feed.dto.TbsubjectDto;
import app.handong.feed.mapper.main.TbsubjectMapper;
import app.handong.feed.service.TbsubjectService;
import org.springframework.stereotype.Service;


@Service
public class TbsubjectServiceImpl implements TbsubjectService {

    private final TbsubjectMapper tbsubjectMapper;

    public TbsubjectServiceImpl(TbsubjectMapper tbsubjectMapper) {
        this.tbsubjectMapper = tbsubjectMapper;
    }

    public TbsubjectDto.DetailResDto getDetail(TbsubjectDto.DetailReqDto param) {
        TbsubjectDto.DetailServDto detailServDto = tbsubjectMapper.getDetailById(param);
        return TbsubjectDto.DetailResDto.builder()
                .subjectId(detailServDto.getSubjectId())
                .like(detailServDto.isLike())
                .lastMessage(tbsubjectMapper.getLastMessageByMessageId(detailServDto.getLastMessageId()))
                .messageHistory(tbsubjectMapper.getMessageHistoryById(detailServDto.getSubjectId()))
                .build();
    }
}
