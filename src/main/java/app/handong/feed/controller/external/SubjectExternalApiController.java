package app.handong.feed.controller.external;

import app.handong.feed.dto.TbSubjectTagDto;
import app.handong.feed.exception.data.DuplicateEntityException;
import app.handong.feed.security.annotation.RequiredApiScopes;
import app.handong.feed.service.ExternalSubjectService;
import app.handong.feed.util.RequestUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/external/subject-tag")
@RequiredArgsConstructor
public class SubjectExternalApiController {
    private final ExternalSubjectService externalSubjectService;

    @PostMapping("/{subjectId}/tag-assign")
    @Operation(summary = "서브젝트에 태그 추가")
    @RequiredApiScopes({"tag_assign:write"})
    public ResponseEntity<TbSubjectTagDto.CreateResDto> addSubjectToTag(
            @PathVariable int subjectId,
            @RequestBody TbSubjectTagDto.ApiCreateReqDto dto,
            HttpServletRequest request
    ) {
        String reqApiId = RequestUtils.getReqApiId(request);


        dto.setTbSubjectId(subjectId);
        return ResponseEntity.ok(externalSubjectService.createSubjectTag(dto.toCreateReqDto(reqApiId)));
    }

    @PostMapping("/{subjectId}/tag-assign-batch")
    @Operation(summary = "서브젝트에 태그 추가")
    @RequiredApiScopes({"tag_assign:write"})
    public ResponseEntity<List<TbSubjectTagDto.CreateResDto>> addSubjectToTags(
            @PathVariable int subjectId,
            @RequestBody List<TbSubjectTagDto.ApiCreateReqDto> dtos,
            HttpServletRequest request
    ) {
        String reqApiId = RequestUtils.getReqApiId(request);
        return ResponseEntity.ok(dtos.stream().map(dto -> {
            try {
                dto.setTbSubjectId(subjectId);
                return externalSubjectService.createSubjectTag(dto.toCreateReqDto(reqApiId));
            } catch (DuplicateEntityException e) {
                return new TbSubjectTagDto.CreateResDto(
                        -1, // 실패를 나타내는 ID
                        dto.getTbSubjectId(),
                        dto.getTagCode(),
                        dto.getConfidentValue(),
                        dto.getForDate(),
                        null // 생성 시간 없음
                );
            }
        }).toList());
    }

    @GetMapping("/latest-for-date")
    @Operation(summary = "가장 최근에 assign 된 for_date 가져오기")
    @RequiredApiScopes({"tag_assign:read"})
    public ResponseEntity<TbSubjectTagDto.GetLatestForDateResDto> getLatestForDate(){
        return ResponseEntity.ok(externalSubjectService.readLatestForDate());
    }
}
