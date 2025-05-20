package app.handong.feed.mapper.archive;

import app.handong.feed.dto.ArchiveDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArchiveNewsletterMapper {
    ArchiveDto.NewsletterDto selectLatestRecord();
}
