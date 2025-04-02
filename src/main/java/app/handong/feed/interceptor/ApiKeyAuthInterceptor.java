package app.handong.feed.interceptor;

import app.handong.feed.security.annotation.RequiredScopes;
import app.handong.feed.config.CustomProperties;
import app.handong.feed.domain.ApiKey;
import app.handong.feed.domain.ApiKeyScope;
import app.handong.feed.repository.ApiKeyRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static app.handong.feed.util.ApiKeyHasher.hmacSha256;

public class ApiKeyAuthInterceptor implements HandlerInterceptor {

    private final ApiKeyRepository apiKeyRepository;
    private final CustomProperties customProperties;

    /**
     * Constructs an ApiKeyAuthInterceptor with the specified API key repository and custom properties.
     *
     * The provided repository is used to retrieve and verify API keys, while the custom properties supply
     * configuration details such as the secret key for hashing API keys.
     *
     * @param apiKeyRepository the repository for retrieving and validating API keys
     * @param customProperties the custom configuration properties for API key authentication
     */
    public ApiKeyAuthInterceptor(ApiKeyRepository apiKeyRepository, CustomProperties customProperties) {
        this.apiKeyRepository = apiKeyRepository;
        this.customProperties = customProperties;
    }

    /**
     * Intercepts an HTTP request to authenticate its API key.
     *
     * <p>This method retrieves the API key from the "X-API-Key" header, hashes it using HMAC SHA-256 
     * with a configured secret, and validates it against the repository. If the key is missing, blank, 
     * invalid, inactive, or lacks the required scopes (when specified via the {@code @RequiredScopes} 
     * annotation on a controller method), the response status is set to 401 Unauthorized or 403 Forbidden 
     * accordingly, and the request is rejected.
     *
     * <p>If the API key passes all checks, it is stored as a request attribute ("authenticatedApiKey") 
     * for subsequent use, and the method returns {@code true}.
     *
     * @param request  the HTTP request containing the API key header
     * @param response the HTTP response used to set an error status if authentication fails
     * @param handler  the handler (typically a controller method) that may specify required scopes
     * @return {@code true} if the API key is valid, active, and meets any required scopes; {@code false} otherwise
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        System.out.println("==========인터셉터!!!======");

        String rawKey = request.getHeader("X-API-Key");

        if (rawKey == null || rawKey.isBlank()) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        String hashedKey = hmacSha256(rawKey, customProperties.getApiSecretKey());
        Optional<ApiKey> apiKeyOpt = apiKeyRepository.findByApiKeyHash(hashedKey);

        if (apiKeyOpt.isEmpty() || !apiKeyOpt.get().isActive()) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }

        // @RequiredScopes 검사
        // 현재 요청이 Controller 메서드에 매핑되어 있는지 검사, 맞으면 HandlerMethod로 캐스팅
        if (handler instanceof HandlerMethod handlerMethod) {
            // 컨트롤러 메서드에 붙은 @RequiredScopes 애노테이션을 꺼냄
            RequiredScopes annotation = handlerMethod.getMethodAnnotation(RequiredScopes.class);
            if (annotation != null) {
                // 애노테이션에 명시된 스코프들을 List<String>으로 변환
                List<String> requiredScopes = List.of(annotation.value());
                // 현재 API 키가 갖고 있는 모든 스코프를 Set<String>으로 정리
                // grantedScopes는 중복 없고 빠른 조회가 필요하므로 Set (requiredScopes는 애노테이션으로부터 바로 받아온 리스트라 List 그대로 사용함)
                Set<String> grantedScopes = apiKeyOpt.get().getScopes().stream()
                        .map(ApiKeyScope::getScope)
                        .collect(Collectors.toSet());
                // requiredScopes 의 모든 요소가 grantedScopes 내에 있는지 확인
                boolean hasAll = grantedScopes.containsAll(requiredScopes);
                if (!hasAll) {
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    return false;
                }
            }
        }

        // 현재 요청(HttpServletRequest)에 인증된 API 키 정보를 저장
        // 컨트롤러나 서비스 단에서 다시 DB 조회 없이 인증된 API 키 정보를 바로 꺼내 쓸 수 있음
        request.setAttribute("authenticatedApiKey", apiKeyOpt.get());
        return true;
    }
}