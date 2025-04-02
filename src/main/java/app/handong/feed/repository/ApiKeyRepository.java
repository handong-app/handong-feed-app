package app.handong.feed.repository;

import app.handong.feed.domain.ApiKey;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
    /**
 * Checks if an API key exists in the database based on the provided hash.
 *
 * @param hash the API key hash to check for existence
 * @return true if an API key with the specified hash exists, false otherwise
 */
boolean existsByApiKeyHash(String hash);
    /**
     * Retrieves an ApiKey entity using its hash while ensuring that the entity's scopes are loaded eagerly.
     *
     * <p>This method uses an entity graph to fetch the "scopes" attribute immediately. This is critical when
     * the ApiKey is obtained outside an active Hibernate session (e.g., at the interceptor layer), preventing
     * LazyInitializationException when accessing lazy-loaded fields such as scopes.</p>
     *
     * @param apiKeyHash the hashed API key used to identify the ApiKey entity
     * @return an Optional containing the matching ApiKey if found, or an empty Optional otherwise
     */
    @EntityGraph(attributePaths = "scopes")
    Optional<ApiKey> findByApiKeyHash(String apiKeyHash);
}