package app.handong.feed.dto;

import app.handong.feed.domain.TbSubjectTag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TbSubjectTagDto {
    @Getter
    @Setter
    public static class CreateReqDto {
        private int tbSubjectId;
        private int tagId;
        private float confidentValue;
        private LocalDate forDate;
        private String updatedBy;

        public TbSubjectTag toEntity(){
            return new TbSubjectTag(this.tbSubjectId, this.tagId, this.confidentValue, this.forDate, this.updatedBy);
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class CreateResDto {
        private int id;
        private int tbSubjectId;
        private int tagId;
        private float confidentValue;
        private LocalDate forDate;
        private LocalDateTime createdAt;
    }

    @Getter
    @Setter
    public static class UpdateReqDto {
        private float confidentValue;
        private LocalDate forDate;
        private String updatedBy;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class UpdateResDto {
        private int id;
        private float confidentValue;
        private LocalDate forDate;
        private String updatedBy;
        private LocalDateTime updatedAt;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ReadResDto {
        private int id;
        private int tbSubjectId;
        private int tagId;
        private float confidentValue;
        private LocalDate forDate;
        private String updatedBy;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class DeleteResDto {
        private int id;
        private String message;
    }

}
