package app.handong.feed.controller;

import app.handong.feed.dto.TbadminDto;
import app.handong.feed.security.annotation.RequiredUserScopes;
import app.handong.feed.security.enums.UserScope;
import app.handong.feed.service.TbadminService;
import app.handong.feed.util.RequestUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/admin")
@RestController
public class TbadminController {
    private final TbadminService tbadminService;

    public TbadminController(TbadminService tbadminService) {
        this.tbadminService = tbadminService;
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
}
