package app.handong.feed.service;

import app.handong.feed.domain.TbUserSearch;
import app.handong.feed.repository.TbUserSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TbLoggerService {
    private final TbUserSearchRepository tbUserSearchRepository;

    @Async
    public void logSearchAsync(String uid, String type, String search) {
        try {
            TbUserSearch searchLog = new TbUserSearch(uid, type, search);
            tbUserSearchRepository.save(searchLog);
        } catch (Exception e) {
            log.warn("Failed to log TbUserSearch asynchronously", e);
            // 실패해도 무시
        }
    }
}
