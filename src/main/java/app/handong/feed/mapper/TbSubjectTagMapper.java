package app.handong.feed.mapper;

import app.handong.feed.dto.TbSubjectTagDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbSubjectTagMapper {
    TbSubjectTagDto.GetLatestForDateResDto getLatestForDate();
}
