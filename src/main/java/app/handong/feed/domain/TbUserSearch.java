package app.handong.feed.domain;

import jakarta.persistence.*;
import org.springframework.scheduling.annotation.Async;

import java.time.LocalDateTime;

@Entity
public class TbUserSearch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private String uid;

    @Lob
    @Column(nullable = false)
    private String search;

    @Column
    private String type; // "all", "unseen", "like", etc. (nullable)

    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }

    // --- Constructors ---
    public TbUserSearch() {}

    public TbUserSearch(String uid, String type, String search) {
        this.uid = uid;
        this.type = type;
        this.search = search;
    }
}
