package app.handong.feed.service.impl;

import app.handong.feed.config.CustomProperties;
import app.handong.feed.domain.ApiKey;
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

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class TbadminServiceImpl implements TbadminService {
    private final TbadminMapper tbadminMapper;
    private final TbUserPermRepository tbUserPermRepository;
    private final FirebaseService firebaseService;
    private final ApiKeyRepository apiKeyRepository;
    private final SecureRandom secureRandom = new SecureRandom();
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
        String hashedKey = hash(rawKey);

        ApiKey apiKey = ApiKey.builder()
                .apiKeyHash(hashedKey)
                .owner(req.getOwner())
                .createdAt(LocalDateTime.now())
                .isActive(true)
                .build();

        for (String scope : req.getScopes()) {
            apiKey.addScope(scope);
        }

        apiKeyRepository.save(apiKey);

        return new TbadminDto.ApiKeyCreateRespDto(rawKey, apiKey.getCreatedAt());
    }

    private String generateRandomSuffix(int length) {
        return secureRandom.ints(length, 0, 36)
                .mapToObj(i -> Integer.toString(i, 36))
                .collect(Collectors.joining());
    }


    private String hash(String input) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec keySpec = new SecretKeySpec(customProperties.getApiSecretKey().getBytes(), "HmacSHA256");
            mac.init(keySpec);
            byte[] digest = mac.doFinal(input.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(digest);
        } catch (Exception e) {
            throw new RuntimeException("HMAC hashing failed", e);
        }
    }

}
