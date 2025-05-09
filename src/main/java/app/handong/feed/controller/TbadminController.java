package app.handong.feed.controller;

import app.handong.feed.dto.TagDto;
import app.handong.feed.dto.TbSubjectTagDto;
import app.handong.feed.dto.TbadminDto;
import app.handong.feed.security.annotation.RequiredUserScopes;
import app.handong.feed.security.enums.UserScope;
import app.handong.feed.service.TagService;
import app.handong.feed.service.TbSubjectTagService;
import app.handong.feed.service.TbadminService;
import app.handong.feed.util.RequestUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/admin")
@RestController
public class TbadminController {
    private final TbadminService tbadminService;
    private final TbSubjectTagService tbSubjectTagService;

    public TbadminController(TbadminService tbadminService, TagService tagService, TbSubjectTagService tbSubjectTagService) {
        this.tbadminService = tbadminService;
        this.tbSubjectTagService = tbSubjectTagService;
    }

    @GetMapping("/users")
    @RequiredUserScopes({
            @RequiredUserScopes.Scope(group = UserScope.ScopeGroup.MEMBER, action = UserScope.ScopeAction.READ)
    })
    public List<TbadminDto.UserDetail> adminGetUser(@RequestParam Map<String, String> param, HttpServletRequest request) {
        String reqUserId = RequestUtils.getReqUserId(request);
        return tbadminService.adminGetUser(reqUserId, param);
    }

    @GetMapping("/firebasefilelist")
    @RequiredUserScopes({
            @RequiredUserScopes.Scope(group = UserScope.ScopeGroup.FILE, action = UserScope.ScopeAction.READ)
    })
    public List<String> adminGetFirebaseStorageList(@RequestParam Map<String, String> param, HttpServletRequest request) {
        String reqUserId = RequestUtils.getReqUserId(request);
        return tbadminService.adminGetFirebaseStorageList(reqUserId);
    }

    @PostMapping("/issue-api-key")
    @RequiredUserScopes({
            @RequiredUserScopes.Scope(group = UserScope.ScopeGroup.APIKEY, action = UserScope.ScopeAction.WRITE)
    })
    public ResponseEntity<TbadminDto.ApiKeyCreateRespDto> issueApiKey(@RequestBody TbadminDto.ApiKeyCreateReqDto dto, HttpServletRequest request) {
        String reqUserId = RequestUtils.getReqUserId(request);
        return ResponseEntity.ok(tbadminService.issueApiKey(reqUserId, dto));
    }

    @GetMapping("/api-keys")
    @RequiredUserScopes({
            @RequiredUserScopes.Scope(group = UserScope.ScopeGroup.APIKEY, action = UserScope.ScopeAction.READ)
    })
    public ResponseEntity<List<TbadminDto.ApiKeyDetail>> getAllApiKeys(HttpServletRequest request) {
        String reqUserId = RequestUtils.getReqUserId(request);
        return ResponseEntity.ok(tbadminService.getAllApiKeyStatus(reqUserId));
    }

    @PatchMapping("/api-keys/{id}/toggle-status")
    @RequiredUserScopes({
            @RequiredUserScopes.Scope(group = UserScope.ScopeGroup.APIKEY, action = UserScope.ScopeAction.WRITE)
    })
    public ResponseEntity<TbadminDto.ApiKeyDetail> toggleApiKeyStatus(@PathVariable String id, HttpServletRequest request) {
        String reqUserId = RequestUtils.getReqUserId(request);
        return ResponseEntity.ok(tbadminService.toggleApiKeyStatus(reqUserId, id));
    }

    @DeleteMapping("/api-keys/{id}")
    @RequiredUserScopes({
            @RequiredUserScopes.Scope(group = UserScope.ScopeGroup.APIKEY, action = UserScope.ScopeAction.DELETE)
    })
    public ResponseEntity<Void> deleteApiKey(@PathVariable String id, HttpServletRequest request) {
        String reqUserId = RequestUtils.getReqUserId(request);
        tbadminService.deleteApiKey(reqUserId, id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/tags")
    @Operation(summary = "태그 생성")
    @RequiredUserScopes({
            @RequiredUserScopes.Scope(group = UserScope.ScopeGroup.TAG, action = UserScope.ScopeAction.WRITE)
    })
    public ResponseEntity<TagDto.CreateResDto> createTag(@RequestBody TagDto.CreateReqDto dto) {
        return ResponseEntity.ok(tbadminService.createTag(dto));
    }

    @PostMapping("/tags/batch")
    @Operation(
            summary = "태그 여러 개 생성",
            description = "여러 개의 태그를 한 번에 생성합니다. 각 태그는 고유한 code 값을 가져야 합니다."
    )
    @RequiredUserScopes({
            @RequiredUserScopes.Scope(group = UserScope.ScopeGroup.TAG, action = UserScope.ScopeAction.WRITE)
    })
    public ResponseEntity<List<TagDto.CreateResDto>> createTags(@RequestBody List<TagDto.CreateReqDto> requestList, HttpServletRequest request) {
        return ResponseEntity.ok(tbadminService.createTags(requestList));
    }

    @PatchMapping("/tags/{code}")
    @Operation(summary = "태그 수정")
    @RequiredUserScopes({
            @RequiredUserScopes.Scope(group = UserScope.ScopeGroup.TAG, action = UserScope.ScopeAction.WRITE)
    })
    public ResponseEntity<TagDto.UpdateResDto> updateTag(@PathVariable String code, @RequestBody TagDto.UpdateReqDto dto) {
        return ResponseEntity.ok(tbadminService.updateTag(code, dto));
    }

    @DeleteMapping("/tags/{code}")
    @Operation(summary = "태그 삭제")
    @RequiredUserScopes({
            @RequiredUserScopes.Scope(group = UserScope.ScopeGroup.TAG, action = UserScope.ScopeAction.DELETE)
    })
    public ResponseEntity<TagDto.DeleteResDto> deleteTag(@PathVariable String code) {
        return ResponseEntity.ok(tbadminService.deleteTag(code));
    }

    @GetMapping("/subject/{subjectId}")
    @Operation(summary = "서브젝트의 태그 조회")
    @RequiredUserScopes({
            @RequiredUserScopes.Scope(group = UserScope.ScopeGroup.TAG_ASSIGN, action = UserScope.ScopeAction.READ)
    })
    public ResponseEntity<List<TbSubjectTagDto.ReadResDto>> getSubjectTag(
            @PathVariable int subjectId
    ) {
        return ResponseEntity.ok(tbSubjectTagService.readSubjectTags(subjectId));
    }

    @PostMapping("/subject/{subjectId}/tag-assign")
    @Operation(summary = "서브젝트에 태그 추가")
    @RequiredUserScopes({
            @RequiredUserScopes.Scope(group = UserScope.ScopeGroup.TAG_ASSIGN, action = UserScope.ScopeAction.WRITE)
    })
    public ResponseEntity<TbSubjectTagDto.CreateResDto> addSubjectToTag(
            @PathVariable int subjectId,
            @RequestBody TbSubjectTagDto.UserCreateReqDto dto,
            HttpServletRequest request
    ) {
        String reqUserId = RequestUtils.getReqUserId(request);

        dto.setTbSubjectId(subjectId);
        return ResponseEntity.ok(tbSubjectTagService.createSubjectTag(dto.toCreateReqDto(reqUserId)));
    }

    @DeleteMapping("/subject/{subjectId}/tag-assign")
    @Operation(summary = "서브젝트에 태그 제거")
    @RequiredUserScopes({
            @RequiredUserScopes.Scope(group = UserScope.ScopeGroup.TAG_ASSIGN, action = UserScope.ScopeAction.WRITE)
    })
    public ResponseEntity<TbSubjectTagDto.DeleteResDto> removeSubjectToTag(
            @PathVariable int subjectId,
            @RequestBody TbSubjectTagDto.DeleteReqDto dto
    ) {
        dto.setTbSubjectId(subjectId);
        return ResponseEntity.ok(tbSubjectTagService.deleteSubjectTag(dto));
    }
}