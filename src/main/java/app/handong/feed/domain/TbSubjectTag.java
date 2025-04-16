package app.handong.feed.domain;

import app.handong.feed.dto.TbSubjectTagDto;
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
                @UniqueConstraint(columnNames = {"tb_subject_id", "tag_code"})
        },
        indexes = {
                @Index(columnList = "tb_subject_id"),
                @Index(columnList = "tag_code")
        }
)
@EntityListeners(AuditingEntityListener.class)
public class TbSubjectTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "tb_subject_id", nullable = false)
    private int tbSubjectId;

    @ManyToOne
    @JoinColumn(
            name = "tb_subject_id",
            referencedColumnName = "id",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private TbSubject tbSubject;

    @Column(name = "tag_code", length = 32, nullable = false)
    private String tagCode; // Tag.code 참조 (FK는 명시적으로 걸지 않음)

    @ManyToOne
    @JoinColumn(
            name = "tag_code",
            referencedColumnName = "code",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Tag tag;

    @Column(name = "confident_value")
    private float confidentValue;

    @Column(name = "for_date")
    private LocalDate forDate;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", length = 32)
    private String updatedBy;

    @Column(name = "updated_by_type", nullable = false)
    private String updatedByType;

    protected TbSubjectTag() {}

    public TbSubjectTag(int tbSubjectId, String tagCode, float confidentValue, LocalDate forDate, String updatedBy, String updatedByType) {
        this.tbSubjectId = tbSubjectId;
        this.tagCode = tagCode;
        this.confidentValue = confidentValue;
        this.forDate = forDate;
        this.updatedBy = updatedBy;
        this.updatedByType = updatedByType;
    }

    public static TbSubjectTag of(int tbSubjectId, String tagCode, float confidentValue, LocalDate forDate, String updatedBy, String updatedByType) {
        return new TbSubjectTag(tbSubjectId, tagCode, confidentValue, forDate, updatedBy, updatedByType);
    }

    public TbSubjectTagDto.CreateResDto toCreateResDto() {
        Integer lastSentAt = tbSubject != null ? tbSubject.getLastSentAt() : null;
        Long lastSentChatId = tbSubject != null ? tbSubject.getLastSentChatId() : null;
        return new TbSubjectTagDto.CreateResDto(
                id,
                tbSubjectId,
                tagCode,
                confidentValue,
                forDate,
                createdAt,
                lastSentAt,
                lastSentChatId
        );
    }
}