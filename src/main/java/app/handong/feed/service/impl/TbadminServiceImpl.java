package app.handong.feed.service.impl;

import app.handong.feed.config.CustomProperties;
import app.handong.feed.domain.ApiKey;
import app.handong.feed.domain.ApiKeyScope;
import app.handong.feed.domain.Tag;
import app.handong.feed.dto.TagDto;
import app.handong.feed.dto.TbadminDto;
import app.handong.feed.exception.data.DuplicateTagCodeException;
import app.handong.feed.exception.data.NotFoundException;
import app.handong.feed.mapper.TbadminMapper;
import app.handong.feed.repository.ApiKeyRepository;
import app.handong.feed.repository.TagRepository;
import app.handong.feed.service.FirebaseService;
import app.handong.feed.service.TbadminService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static app.handong.feed.util.ApiKeyHasher.hmacSha256;
import static app.handong.feed.util.KeyGenerator.generateRandomSuffix;


@Service
public class TbadminServiceImpl implements TbadminService {
    private final TbadminMapper tbadminMapper;
    private final FirebaseService firebaseService;
    private final ApiKeyRepository apiKeyRepository;
    private final CustomProperties customProperties;
    private final TagRepository tagRepository;


    public TbadminServiceImpl(TbadminMapper tbadminMapper, FirebaseService firebaseService, ApiKeyRepository apiKeyRepository, CustomProperties customProperties, TagRepository tagRepository) {
        this.tbadminMapper = tbadminMapper;
        this.firebaseService = firebaseService;
        this.apiKeyRepository = apiKeyRepository;
        this.customProperties = customProperties;
        this.tagRepository = tagRepository;
    }

    @Override
    public List<TbadminDto.UserDetail> adminGetUser(String userId, Map<String, String> param) {
        return tbadminMapper.allUsers();
    }

    @Override
    public List<String> adminGetFirebaseStorageList(String userId) {
        return firebaseService.listAllFiles("KaFile");
    }

    @Override
    @Transactional
    public TbadminDto.ApiKeyCreateRespDto issueApiKey(String userId, TbadminDto.ApiKeyCreateReqDto req) {
        String rawKey = UUID.randomUUID().toString().replace("-", "") + generateRandomSuffix(8);
        String hashedKey = hmacSha256(rawKey,  customProperties.getApiSecretKey());

        ApiKey apiKey = ApiKey.builder()
                .apiKeyHash(hashedKey)
                .description(req.getDescription())
                .issuedBy(userId)
                .createdAt(LocalDateTime.now())
                .isActive(true)
                .build();

        for (String scope : req.getScopes()) {
            apiKey.addScope(scope);
        }

        apiKeyRepository.save(apiKey);

        return new TbadminDto.ApiKeyCreateRespDto(rawKey, apiKey.getCreatedAt());
    }

    @Override
    @Transactional
    public List<TbadminDto.ApiKeyDetail> getAllApiKeyStatus(String userId) {
        List<ApiKey> apiKeys = apiKeyRepository.findAll();
        return apiKeys.stream().map((apiKey) -> {
            List<String> scopes = apiKey.getScopes().stream()
                    .map(ApiKeyScope::getScope)
                    .toList();
            return new TbadminDto.ApiKeyDetail(
                    apiKey.getId(),
                    apiKey.getDescription(),
                    apiKey.getIssuedUser().toUserDetail(),
                    apiKey.isActive(),
                    apiKey.getCreatedAt(),
                    apiKey.getLastUsedAt(),
                    scopes
            );   }).toList();
    }

    @Override
    @Transactional
    public TbadminDto.ApiKeyDetail toggleApiKeyStatus(String userId, String apiKeyId) {
        ApiKey apiKey = apiKeyRepository.findById(apiKeyId)
                .orElseThrow(() -> new IllegalArgumentException("API key not found"));

        apiKey.setActive(!apiKey.isActive());

        apiKeyRepository.save(apiKey);

        List<String> scopes = apiKey.getScopes().stream()
                .map(ApiKeyScope::getScope)
                .toList();

        return new TbadminDto.ApiKeyDetail(
                apiKey.getId(),
                apiKey.getDescription(),
                apiKey.getIssuedUser().toUserDetail(),
                apiKey.isActive(),
                apiKey.getCreatedAt(),
                apiKey.getLastUsedAt(),
                scopes
        );
    }

    @Override
    @Transactional
    public void deleteApiKey(String userId, String apiKeyId) {
        ApiKey apiKey = apiKeyRepository.findById(apiKeyId)
                .orElseThrow(() -> new IllegalArgumentException("API key not found"));
        apiKeyRepository.delete(apiKey);
    }

    @Override
    public TagDto.CreateResDto createTag(TagDto.CreateReqDto dto) {
        if (tagRepository.existsById(dto.getCode())) {
            throw new DuplicateTagCodeException(dto.getCode());
        }

        return TagDto.CreateResDto.fromEntity(tagRepository.save(dto.toEntity()));
    }

    @Override
    public List<TagDto.CreateResDto> createTags(List<TagDto.CreateReqDto> requestList) {
        List<Tag> tags = requestList.stream()
                .map(TagDto.CreateReqDto::toEntity)
                .toList();

        for (Tag tag : tags) {
            if (tagRepository.existsById(tag.getCode())) {
                throw new DuplicateTagCodeException(tag.getCode());
            }
        }

        List<Tag> savedTags = tagRepository.saveAll(tags);
        return savedTags.stream()
                .map(TagDto.CreateResDto::fromEntity)
                .toList();
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public TagDto.UpdateResDto updateTag(String code, TagDto.UpdateReqDto dto) {
        Tag tag = tagRepository.findById(code)
                .orElseThrow(() -> new NotFoundException("Tag not found with code: " + code));

        // 필드 업데이트
        tag.setLabel(dto.getLabel());
        tag.setUserDesc(dto.getUserDesc());
        tag.setLlmDesc(dto.getLlmDesc());
        tag.setColorHex(dto.getColorHex());
        tag.setPriorityWeight(dto.getPriorityWeight());

        return new TagDto.UpdateResDto(
                tag.getCode(),
                tag.getLabel(),
                tag.getUserDesc(),
                tag.getLlmDesc(),
                tag.getColorHex(),
                tag.getPriorityWeight(),
                tag.getUpdatedAt()
        );
    }

    @Override
    public TagDto.DeleteResDto deleteTag(String code) {
        tagRepository.findById(code)
                .orElseThrow(() -> new NotFoundException("Tag not found with code: " + code));

        tagRepository.deleteById(code);
        return new TagDto.DeleteResDto(code, "Tag deleted successfully");
    }

}
