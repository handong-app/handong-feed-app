package app.handong.feed.service.impl;

import app.handong.feed.dto.ArchiveDto;
import app.handong.feed.mapper.archive.ArchiveNewsletterMapper;
import app.handong.feed.service.ArchiveService;
import org.springframework.stereotype.Service;

@Service
public class ArchiveServiceImpl implements ArchiveService {
    private final ArchiveNewsletterMapper archiveNewsletterMapper;

    public ArchiveServiceImpl(ArchiveNewsletterMapper archiveNewsletterMapper) {
        this.archiveNewsletterMapper = archiveNewsletterMapper;
    }

    @Override
    public ArchiveDto.NewsletterDto getLastNewsletter() {
        return archiveNewsletterMapper.selectLatestRecord();

    }
}
