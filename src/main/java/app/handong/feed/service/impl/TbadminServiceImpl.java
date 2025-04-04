package app.handong.feed.service.impl;

import app.handong.feed.config.CustomProperties;
import app.handong.feed.domain.ApiKey;
import app.handong.feed.domain.ApiKeyScope;
import app.handong.feed.dto.TbadminDto;
import app.handong.feed.exception.auth.NoAuthorizationException;
import app.handong.feed.id.UserPermId;
import app.handong.feed.mapper.TbadminMapper;
import app.handong.feed.repository.ApiKeyRepository;
import app.handong.feed.repository.TbUserPermRepository;
import app.handong.feed.service.FirebaseService;
import app.handong.feed.service.TbadminService;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static app.handong.feed.util.ApiKeyHasher.hmacSha256;
import static app.handong.feed.util.KeyGenerator.generateRandomSuffix;


@Service
public class TbadminServiceImpl implements TbadminService {
    private final TbadminMapper tbadminMapper;
    private final TbUserPermRepository tbUserPermRepository;
    private final FirebaseService firebaseService;
    private final ApiKeyRepository apiKeyRepository;
    private final CustomProperties customProperties;


    public TbadminServiceImpl(TbadminMapper tbadminMapper, TbUserPermRepository tbUserPermRepository, FirebaseService firebaseService, ApiKeyRepository apiKeyRepository, CustomProperties customProperties) {
        this.tbadminMapper = tbadminMapper;
        this.tbUserPermRepository = tbUserPermRepository;
        this.firebaseService = firebaseService;
        this.apiKeyRepository = apiKeyRepository;
        this.customProperties = customProperties;
    }

    @Override
    public List<TbadminDto.UserDetail> adminGetUser(String userId, Map<String, String> param) {

        if (tbUserPermRepository.findById(new UserPermId(userId, "adminGetUser")).isEmpty())
            throw new NoAuthorizationException("No Admin Permission");
        return tbadminMapper.allUsers();
    }

    @Override
    public List<String> adminGetFirebaseStorageList(String userId) {

        if (tbUserPermRepository.findById(new UserPermId(userId, "adminFirebaseFiles")).isEmpty())
            throw new NoAuthorizationException("No Admin Permission");
        return firebaseService.listAllFiles("KaFile");
    }

    @Override
    @Transactional
    public TbadminDto.ApiKeyCreateRespDto issueApiKey(String userId, TbadminDto.ApiKeyCreateReqDto req) {
        if (tbUserPermRepository.findById(new UserPermId(userId, "adminIssueApiKey")).isEmpty())
            throw new NoAuthorizationException("No Admin Permission");

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
        if (tbUserPermRepository.findById(new UserPermId(userId, "adminReadAllApiKey")).isEmpty()) {
            throw new NoAuthorizationException("No Admin Permission");
        }
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
    public TbadminDto.ApiKeyDetail toggleApiKeyStatus(String userId, Long apiKeyId) {
        if (tbUserPermRepository.findById(new UserPermId(userId, "adminToggleApiKey")).isEmpty()) {
            throw new NoAuthorizationException("No Admin Permission");
        }

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
    public void deleteApiKey(String userId, Long apiKeyId) {
        if (tbUserPermRepository.findById(new UserPermId(userId, "adminDeleteApiKey")).isEmpty()) {
            throw new NoAuthorizationException("No Admin Permission");
        }

        ApiKey apiKey = apiKeyRepository.findById(apiKeyId)
                .orElseThrow(() -> new IllegalArgumentException("API key not found"));

        apiKeyRepository.delete(apiKey);
    }

}
