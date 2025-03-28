package app.handong.feed.controller;

import app.handong.feed.dto.TagDto;
import app.handong.feed.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping
    @Operation(summary = "태그 생성")
    public ResponseEntity<TagDto.CreateResDto> createTag(@RequestBody TagDto.CreateReqDto dto) {
        return ResponseEntity.ok(tagService.createTag(dto));
    }

    @PostMapping("/batch")
    @Operation(
            summary = "태그 여러 개 생성",
            description = "여러 개의 태그를 한 번에 생성합니다. 각 태그는 고유한 code 값을 가져야 합니다."
    )
    public ResponseEntity<List<TagDto.CreateResDto>> createTags(@RequestBody List<TagDto.CreateReqDto> requestList) {
        return ResponseEntity.ok(tagService.createTags(requestList));
    }

    @GetMapping("/{code}")
    @Operation(summary = "태그 단건 조회")
    public ResponseEntity<TagDto.ReadResDto> readTag(@PathVariable String code) {
        return ResponseEntity.ok(tagService.readTag(code));
    }

    @GetMapping
    @Operation(summary = "전체 태그 목록 조회")
    public ResponseEntity<List<TagDto.ReadResDto>> readAllTags() {
        return ResponseEntity.ok(tagService.readAllTags());
    }

    @PatchMapping("/{code}")
    @Operation(summary = "태그 수정")
    public ResponseEntity<TagDto.UpdateResDto> updateTag(@PathVariable String code,
                                                         @RequestBody TagDto.UpdateReqDto dto) {
        return ResponseEntity.ok(tagService.updateTag(code, dto));
    }

    @DeleteMapping("/{code}")
    @Operation(summary = "태그 삭제")
    public ResponseEntity<TagDto.DeleteResDto> deleteTag(@PathVariable String code) {
        return ResponseEntity.ok(tagService.deleteTag(code));
    }
}