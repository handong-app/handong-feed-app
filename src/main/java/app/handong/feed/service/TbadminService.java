package app.handong.feed.service;

import app.handong.feed.dto.TagDto;
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
    TbadminDto.ApiKeyDetail toggleApiKeyStatus(String userId, String apiKeyId);
    void deleteApiKey(String userId, String apiKeyId);

    TagDto.CreateResDto createTag(TagDto.CreateReqDto dto);
    List<TagDto.CreateResDto> createTags(List<TagDto.CreateReqDto> requestList);
    TagDto.UpdateResDto updateTag(String code, TagDto.UpdateReqDto dto);
    TagDto.DeleteResDto deleteTag(String code);

}
