package app.handong.feed.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "api_key_scopes")
public class ApiKeyScope {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "api_key_id", nullable = false)
    private ApiKey apiKey;

    @Column(nullable = false)
    private String scope;

    public ApiKeyScope() {}

    public ApiKeyScope(ApiKey apiKey, String scope) {
        this.apiKey = apiKey;
        this.scope = scope;
    }

}