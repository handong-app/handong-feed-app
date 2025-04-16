package app.handong.feed.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;

@Entity
@Table(name = "TbSubject")
@Getter
@ToString
@Immutable // 완전 불변 객체로 간주 (Hibernate 전용)
public class TbSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "last_sent_at")
    private Integer lastSentAt;

    @Column(name = "last_sent_chat_id")
    private Long lastSentChatId;

    @Column(name = "deleted")
    private String deleted;
}
