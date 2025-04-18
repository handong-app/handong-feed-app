package app.handong.feed.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "api_keys")
public class ApiKey {

    @Id
    @Column(columnDefinition = "char(32)")
    private String id; // UUID

    @Column(name = "api_key_hash", nullable = false, columnDefinition = "char(64)")
    private String apiKeyHash;

    private String description;

    @Column(columnDefinition = "varchar(32)")
    private String issuedBy;

    @ManyToOne
    @JoinColumn(
            name = "issuedBy",
            referencedColumnName = "id",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Tbuser issuedUser;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime lastUsedAt;
    @Setter private boolean isActive = true;

    @Builder.Default // @Builder를 쓰면 필드 초기값이 무시되기 때문에, 빌더로 생성할 때도 기본값을 써라는 명시적 힌트 추가함.
    @OneToMany(mappedBy = "apiKey", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApiKeyScope> scopes = new ArrayList<>();

    public ApiKey() {}

    public void addScope(String scopeName) {
        this.scopes.add(new ApiKeyScope(this, scopeName));
    }

    @PrePersist
    public void onPrePersist() {
        if (this.id == null || this.id.isEmpty()) {
            this.id = UUID.randomUUID().toString().replace("-", "");
        }
    }

    public void updateLastUsedAt() {
        this.lastUsedAt = LocalDateTime.now();
    }

}