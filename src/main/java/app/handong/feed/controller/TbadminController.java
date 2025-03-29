package app.handong.feed.controller;

import app.handong.feed.dto.TbadminDto;
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
    public List<TbadminDto.UserDetail> adminGetUser(@RequestParam Map<String, String> param, HttpServletRequest request) {
        String reqUserId = RequestUtils.getReqUserId(request);
        return tbadminService.adminGetUser(reqUserId, param);
    }

    @GetMapping("/firebasefilelist")
    public List<String> adminGetFirebaseStorageList(@RequestParam Map<String, String> param, HttpServletRequest request) {
        String reqUserId = RequestUtils.getReqUserId(request);
        return tbadminService.adminGetFirebaseStorageList(reqUserId);
    }

    @PostMapping("/issue-api-key")
    public ResponseEntity<TbadminDto.ApiKeyCreateRespDto> issueApiKey(@RequestBody TbadminDto.ApiKeyCreateReqDto dto, HttpServletRequest request) {
        String reqUserId = RequestUtils.getReqUserId(request);
        return ResponseEntity.ok(tbadminService.issueApiKey(reqUserId, dto));
    }
}
