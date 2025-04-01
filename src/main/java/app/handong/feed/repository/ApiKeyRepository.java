package app.handong.feed.repository;

import app.handong.feed.domain.ApiKey;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
    boolean existsByApiKeyHash(String hash);
    /*
    Spring의 @Transactional은 컨트롤러/서비스 레이어 이후에서만 프록시 세션을 유지한다.
    따라서 인터셉터 레벨에서 ApiKeyRepository.findByApiKeyHash(...)를 호출하면,
    해당 ApiKey 엔티티는 Hibernate 세션 밖에서 로딩되므로 지연 로딩(LAZY) 필드 접근 시 문제가 발생한다.
    예를 들어 ApiKey.scopes가 LAZY 설정인 경우, getScopes()를 호출하면 세션이 이미 닫혀 있어 LazyInitializationException이 발생한다.
    => 이를 해결하기 위해 @EntityGraph를 사용해 ApiKey.scopes를 즉시(fetch join) 로딩하도록 지정한다.
     */
    @EntityGraph(attributePaths = "scopes")
    Optional<ApiKey> findByApiKeyHash(String apiKeyHash);
}