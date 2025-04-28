package app.handong.feed.mapper;

import app.handong.feed.dto.TbsubjectDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TbsubjectMapper {
    TbsubjectDto.DetailServDto getDetailById(TbsubjectDto.DetailReqDto reqDto);

    TbsubjectDto.LastMessageServDto getLastMessageByMessageId(String lastMessageId);

    List<TbsubjectDto.MessageHistoryServDto> getMessageHistoryById(Long subjectId);

    /**
     * 주제의 태그 할당 상태를 완료(true)로 업데이트합니다.
     * @param id 대상 주제의 ID
     */
    int updateIsTagAssignedTrue(Long id);
}
