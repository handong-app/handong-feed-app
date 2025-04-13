package app.handong.feed.controller.external;

import app.handong.feed.dto.TagDto;
import app.handong.feed.security.annotation.RequiredApiScopes;
import app.handong.feed.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/external/tag")
@RequiredArgsConstructor
public class TagExternalApiController {
    private final TagService tagService;

    @GetMapping
    @Operation(summary = "전체 태그 목록 조회")
    @RequiredApiScopes({"tag:read"})
    public ResponseEntity<List<TagDto.ReadResDto>> readAllTags() {
        return ResponseEntity.ok(tagService.readAllTags());
    }
}
