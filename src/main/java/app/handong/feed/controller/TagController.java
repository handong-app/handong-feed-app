package app.handong.feed.controller;

import app.handong.feed.dto.TagDto;
import app.handong.feed.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping("/report")
    @Operation(summary = "태그 신고")
    public ResponseEntity<String> reportTag(@Valid @RequestBody TagDto.ReportReqDto tagDto, HttpServletRequest request) {
        tagService.reportTag(tagDto, request.getAttribute("reqUserId").toString());
        return ResponseEntity.ok("{}");
    }


    @GetMapping("/get/{code}")
    @Operation(summary = "태그 단건 조회")
    public ResponseEntity<TagDto.ReadResDto> readTag(@PathVariable String code) {
        return ResponseEntity.ok(tagService.readTag(code));
    }

    @GetMapping
    @Operation(summary = "전체 태그 목록 조회")
    public ResponseEntity<List<TagDto.ReadResDto>> readAllTags() {
        return ResponseEntity.ok(tagService.readAllTags());
    }
}