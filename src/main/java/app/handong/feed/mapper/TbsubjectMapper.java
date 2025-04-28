package app.handong.feed.mapper;

import app.handong.feed.dto.TbsubjectDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TbsubjectMapper {
    TbsubjectDto.DetailServDto getDetailById(TbsubjectDto.DetailReqDto reqDto);

    TbsubjectDto.LastMessageServDto getLastMessageByMessageId(String lastMessageId);

    List<TbsubjectDto.MessageHistoryServDto> getMessageHistoryById(Long subjectId);

    void updateIsTagAssignedTrue(Long id);
}
