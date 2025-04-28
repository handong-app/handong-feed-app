package app.handong.feed.controller.external;

import app.handong.feed.security.annotation.RequiredApiScopes;
import app.handong.feed.service.ExternalSubjectService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/external/subject")
@RequiredArgsConstructor
public class SubjectExternalApiController {
    private final ExternalSubjectService externalSubjectService;

    @PatchMapping("/{subjectId}/tag-assigned")
    @Operation(summary = "주제 ID를 기반으로 태그 할당 완료 상태로 갱신합니다.")
    @RequiredApiScopes({"tag_assign:write"})
    public ResponseEntity<Void> updateIsTagAssignedTrue(
            @PathVariable long subjectId
    ) {
        externalSubjectService.updateIsTagAssignedTrue(subjectId);
        return ResponseEntity.noContent().build();
    }
}
