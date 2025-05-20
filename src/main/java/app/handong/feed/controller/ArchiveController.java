package app.handong.feed.controller;

import app.handong.feed.dto.ArchiveDto;
import app.handong.feed.service.ArchiveService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/archive")
public class ArchiveController {
    private final ArchiveService archiveService;

    public ArchiveController(ArchiveService archiveService) {
        this.archiveService = archiveService;
    }

    @GetMapping("/newsletter")
    @Operation(summary = "전체 태그 목록 조회")
    public ResponseEntity<ArchiveDto.NewsletterDto> getLastNewsletter() {
        return ResponseEntity.ok(archiveService.getLastNewsletter());
    }

}
