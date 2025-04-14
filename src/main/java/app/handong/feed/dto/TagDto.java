package app.handong.feed.dto;

import app.handong.feed.domain.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

public class TagDto {
    private TagDto() {} // 불필요한 인스턴스화 방지를 위함. (DTO 모음 유틸클레스임을 알림)

    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateReqDto {

        @NotBlank
        @Size(max = 32)
        private String code;

        @NotBlank
        @Size(max = 32)
        private String label;

        @Size(max = 1000)
        private String userDesc;

        @Size(max = 1000)
        private String llmDesc;

        @Size(min = 6, max = 6)
        private String colorHex;

        private float priorityWeight;

        public Tag toEntity() {
            return new Tag(code, label, userDesc, llmDesc, colorHex, priorityWeight);
        }
    }

    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateResDto {
        private String code;
        private String label;
        private String userDesc;
        private String llmDesc;
        private String colorHex;
        private float priorityWeight;
        private LocalDateTime createdAt;

        public static CreateResDto fromEntity(Tag tag) {
            return new CreateResDto(
                    tag.getCode(),
                    tag.getLabel(),
                    tag.getUserDesc(),
                    tag.getLlmDesc(),
                    tag.getColorHex(),
                    tag.getPriorityWeight(),
                    tag.getCreatedAt()
            );
        }
    }

    @Getter
    @Setter
    public static class UpdateReqDto {
        private String label;
        private String userDesc;
        private String llmDesc;
        private String colorHex;
        private float priorityWeight;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class UpdateResDto {
        private String code;
        private String label;
        private String userDesc;
        private String llmDesc;
        private String colorHex;
        private float priorityWeight;
        private LocalDateTime updatedAt;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ReadResDto {
        private String code;
        private String label;
        private String userDesc;
        private String llmDesc;
        private String colorHex;
        private float priorityWeight;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class DeleteResDto {
        private String code;
        private String message;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ReadUserResDto {
        private String code;
        private String label;
        private String userDesc;
        private String colorHex;
    }
}