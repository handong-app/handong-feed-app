package app.handong.feed.repository;

import app.handong.feed.domain.ApiKeyScope;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApiKeyScopeRepository extends JpaRepository<ApiKeyScope, Long> {
    List<ApiKeyScope> findByScope(String scope);
}
