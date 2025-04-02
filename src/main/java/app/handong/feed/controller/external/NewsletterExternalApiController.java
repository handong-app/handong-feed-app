package app.handong.feed.controller.external;

import app.handong.feed.dto.TagDto;
import app.handong.feed.security.annotation.RequiredScopes;
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

    /**
     * Retrieves the details of a tag identified by its unique code.
     *
     * <p>This method fetches the tag from the tag service and returns the details wrapped in a ResponseEntity with an
     * HTTP 200 OK status. Access to this endpoint requires the 'tag:read' scope.
     *
     * @param code the unique identifier of the tag
     * @return a ResponseEntity containing the tag details
     */
    @GetMapping("/tags/{code}")
    @Operation(summary = "태그 단건 조회")
    @RequiredScopes({"tag:read"})
    public ResponseEntity<TagDto.ReadResDto> readTag(@PathVariable String code) {
        return ResponseEntity.ok(tagService.readTag(code));
    }

    /**
     * Retrieves a list of all newsletter tags.
     *
     * <p>This endpoint returns a list of all tags available in the newsletter system, each represented
     * as a TagDto.ReadResDto object. The list is wrapped in a ResponseEntity with an HTTP 200 OK status.</p>
     *
     * @return a ResponseEntity containing the list of all newsletter tags
     */
    @GetMapping("/tags")
    @Operation(summary = "전체 태그 목록 조회")
    @RequiredScopes({"tag:read"})
    public ResponseEntity<List<TagDto.ReadResDto>> readAllTags() {
        return ResponseEntity.ok(tagService.readAllTags());
    }
}
