package app.handong.feed.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(
        name = "tb_subject_tag",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"tb_subject_id", "tag_id"})
        },
        indexes = {
                @Index(columnList = "tb_subject_id"),
                @Index(columnList = "tag_id")
        }
)
@EntityListeners(AuditingEntityListener.class)
public class TbSubjectTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "tb_subject_id", nullable = false)
    private int tbSubjectId;

    @Column(name = "tag_id", nullable = false)
    private int tagId;

    @Column(name = "confident_value")
    private float confidentValue; // 적합도

    @Column(name = "for_date", nullable = false)
    private LocalDate forDate; // 실제로 주제가 생성된 Date

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", length = 32)
    private String updatedBy;

    protected TbSubjectTag() {
    }

    public TbSubjectTag(int tbSubjectId, int tagId, float confidentValue, LocalDate forDate, String updatedBy) {
        this.tbSubjectId = tbSubjectId;
        this.tagId = tagId;
        this.confidentValue = confidentValue;
        this.forDate = forDate;
        this.updatedBy = updatedBy;
    }

    public static TbSubjectTag of(int tbSubjectId, int tagId, float confidentValue, LocalDate forDate, String updatedBy) {
        return new TbSubjectTag(tbSubjectId, tagId, confidentValue, forDate, updatedBy);
    }
}