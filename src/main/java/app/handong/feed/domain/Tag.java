package app.handong.feed.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Tag {

    @Id
    @Setter
    @Column(length = 32)
    private String code; // kebab-case, 태그 식별자, 영문

    @Setter
    @Column(nullable = false, length = 32)
    private String label; // 유저 라벨, 국문

    @Setter
    @Column(name = "user_desc", columnDefinition = "TEXT")
    private String userDesc; // 사용자용 설명

    @Setter
    @Column(name = "llm_desc", columnDefinition = "TEXT")
    private String llmDesc; // LLM용 설명

    @Setter
    @Column(name = "color_hex", length = 6)
    private String colorHex; // HEX 색상 (# 제외)

    @Setter
    @Column(name = "priority_weight")
    private float priorityWeight; // 우선순위 가중치

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    protected Tag() {}

    public Tag(String code, String label, String userDesc, String llmDesc, String colorHex, float priorityWeight) {
        this.code = code;
        this.label = label;
        this.userDesc = userDesc;
        this.llmDesc = llmDesc;
        this.colorHex = colorHex;
        this.priorityWeight = priorityWeight;
    }

    public static Tag of(String code, String label, String userDesc, String llmDesc, String colorHex, float priorityWeight) {
        return new Tag(code, label, userDesc, llmDesc, colorHex, priorityWeight);
    }
}