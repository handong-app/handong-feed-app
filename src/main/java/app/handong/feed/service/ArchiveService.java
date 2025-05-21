package app.handong.feed.service;

import app.handong.feed.dto.ArchiveDto;

public interface ArchiveService {
    ArchiveDto.NewsletterDto getLastNewsletter();
}
