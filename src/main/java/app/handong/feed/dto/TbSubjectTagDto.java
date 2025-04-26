package app.handong.feed.dto;

import app.handong.feed.domain.TbSubjectTag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TbSubjectTagDto {
    private TbSubjectTagDto() {}

    @Getter
    @Setter
    public static class UserCreateReqDto {
        private int tbSubjectId;
        private String tagCode;
        private LocalDate forDate;

        public CreateReqDto toCreateReqDto(String updatedBy) {
            return new CreateReqDto(tbSubjectId, tagCode, 1, forDate, updatedBy, "user");
        }
    }

    @Getter
    @Setter
    public static class ApiCreateReqDto {
        private int tbSubjectId;
        private String tagCode;
        private LocalDate forDate;
        private float confidentValue;

        public CreateReqDto toCreateReqDto(String updatedBy) {
            return new CreateReqDto(tbSubjectId, tagCode, confidentValue, forDate, updatedBy, "api");
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class CreateReqDto {
        private int tbSubjectId;
        private String tagCode;
        private float confidentValue;
        private LocalDate forDate;
        private String updatedBy;
        private String updatedByType;

        public TbSubjectTag toEntity() {
            return new TbSubjectTag(tbSubjectId, tagCode, confidentValue, forDate, updatedBy, updatedByType);
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class CreateResDto {
        private int id;
        private int tbSubjectId;
        private String tagCode;
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
        private String tagCode;
        private TagDto.ReadResDto tagData;
        private float confidentValue;
        private LocalDate forDate;
        private String updatedBy;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class GetLatestForDateResDto {
        private LocalDate latestForDate;
    }

    @Getter
    @Setter
    public static class DeleteReqDto {
        private int tbSubjectId;
        private String tagCode;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class DeleteResDto {
        private int id;
        private String message;
    }
}