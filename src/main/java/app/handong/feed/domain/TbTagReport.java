package app.handong.feed.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class) // For Audit
public class TbTagReport {
    @Id
    private String id;

    @Setter
    @Column(nullable = false)
    private String deleted; // 삭제여부

    @Setter
    @Column(nullable = false)
    private String subjectId;

    @Setter
    @Column(nullable = false, length = 2000000)
    @Lob
    private String reportMessage; // 원본 메세지 본문

    @Setter
    @Column(nullable = false)
    private String reportedBy;

    @Setter
    @Column
    private String resolvedBy;

    @Setter
    @Column(length = 2000000)
    @Lob
    private String resolvedMessage; // 원본 메세지 본문

    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime modifiedDate;


    protected TbTagReport() {
    }

    public TbTagReport(String subjectId, String message, String reportedBy, String resolvedBy, String resolvedMessage) {
        this.subjectId = subjectId;
        this.reportMessage = message;
        this.reportedBy = reportedBy;
        this.resolvedBy = resolvedBy;
        this.resolvedMessage = resolvedMessage;
    }

    public static TbTagReport of(String subjectId, String message, String reportedBy) {
        return new TbTagReport(subjectId, message, reportedBy, null, null);
    }


    @PrePersist
    public void onPrePersist() {
        this.id = UUID.randomUUID().toString().replace("-", "");
        this.deleted = "N";
    }
}
