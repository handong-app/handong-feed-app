package app.handong.feed.service;

import app.handong.feed.dto.TbadminDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface TbadminService {
    public List<TbadminDto.UserDetail> adminGetUser(String userId, Map<String, String> param);

    public List<String> adminGetFirebaseStorageList(String userId);

    List<TbadminDto.ApiKeyDetail> getAllApiKeyStatus(String userId);
    TbadminDto.ApiKeyCreateRespDto issueApiKey(String userId, TbadminDto.ApiKeyCreateReqDto req);
    TbadminDto.ApiKeyDetail toggleApiKeyStatus(String userId, Long apiKeyId);
    void deleteApiKey(String userId, Long apiKeyId);
}
