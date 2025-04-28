package app.handong.feed.controller.external;

import app.handong.feed.security.annotation.RequiredApiScopes;
import app.handong.feed.service.ExternalSubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/external/subject")
@RequiredArgsConstructor
public class SubjectExternalApiController {
    private final ExternalSubjectService externalSubjectService;


    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "태그 할당 상태가 성공적으로 업데이트됨"),
            @ApiResponse(responseCode = "404", description = "지정된 ID의 주제를 찾을 수 없음"),
            @ApiResponse(responseCode = "403", description = "API 접근 권한 없음")
    })
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
