package app.handong.feed.controller.external;

import app.handong.feed.dto.TagDto;
import app.handong.feed.security.annotation.RequiredApiScopes;
import app.handong.feed.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/external/newsletter")
@RequiredArgsConstructor
public class NewsletterExternalApiController {

    private final TagService tagService;

    @GetMapping("/tags/{code}")
    @Operation(summary = "태그 단건 조회")
    @RequiredApiScopes({"tag:read"})
    public ResponseEntity<TagDto.ReadResDto> readTag(@PathVariable String code) {
        return ResponseEntity.ok(tagService.readTag(code));
    }

    @GetMapping("/tags")
    @Operation(summary = "전체 태그 목록 조회")
    @RequiredApiScopes({"tag:read"})
    public ResponseEntity<List<TagDto.ReadResDto>> readAllTags() {
        return ResponseEntity.ok(tagService.readAllTags());
    }
}
