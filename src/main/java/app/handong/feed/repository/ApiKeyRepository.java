package app.handong.feed.repository;

import app.handong.feed.domain.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
    boolean existsByApiKeyHash(String hash);
    Optional<ApiKey> findByApiKeyHash(String hash);
}