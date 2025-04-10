package app.handong.feed.controller.external;

import app.handong.feed.dto.ExternalDto;
import app.handong.feed.dto.TbmessageDto;
import app.handong.feed.security.annotation.RequiredApiScopes;
import app.handong.feed.service.ExternalFeedService;
import app.handong.feed.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/external/feed")
@RequiredArgsConstructor
public class FeedExternalApiController {
    private final TagService tagService;
    private final ExternalFeedService externalFeedService;

    @GetMapping
    @Operation(summary = "피드 목록 조회")
    @RequiredApiScopes({"feed:read"})
    public ResponseEntity<List<TbmessageDto.Detail>> readFeed(@ModelAttribute ExternalDto.FeedReqDto dto) {
        return ResponseEntity.ok(externalFeedService.getFeedBetween(dto));
    }
}
