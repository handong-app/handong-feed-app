package app.handong.feed.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 32)
    private String name; // 태그 값

    @Column(name = "name_kor", nullable = false, length = 32)
    private String nameKor; // 태그 한국어 값

    @Column(columnDefinition = "TEXT")
    private String desc; // 태그 설명

    @Column(length = 32)
    private String color; // 태그 색상

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // 생성일

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // 수정일

    protected Tag() {}

    public Tag(String name, String nameKor, String desc, String color) {
        this.name = name;
        this.nameKor = nameKor;
        this.desc = desc;
        this.color = color;
    }

    public static Tag of(String name, String nameKor, String desc, String color) {
        return new Tag(name, nameKor, desc, color);
    }
}