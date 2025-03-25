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
    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateReqDto {

        @NotBlank(message = "태그 이름은 필수입니다.")
        @Size(max = 32, message = "태그 이름은 32자 이내여야 합니다.")
        private String name;

        @NotBlank(message = "태그 한국어 이름은 필수입니다.")
        @Size(max = 32, message = "태그 한국어 이름은 32자 이내여야 합니다.")
        private String nameKor;

        @Size(max = 1000, message = "설명은 1000자 이내여야 합니다.")
        private String desc;

        @Size(max = 32, message = "색상 값은 32자 이내여야 합니다.")
        private String color;

        public Tag toEntity() {
            return new Tag(this.name, this.nameKor, this.desc, this.color);
        }
    }

    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateResDto {
        private int id;
        private String name;
        private String nameKor;
        private String desc;
        private String color;
        private LocalDateTime createdAt;
    }

    @Getter
    @Setter
    public static class UpdateReqDto {
        private String name;
        private String nameKor;
        private String desc;
        private String color;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class UpdateResDto {
        private int id;
        private String name;
        private String nameKor;
        private String desc;
        private String color;
        private LocalDateTime updatedAt;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ReadResDto {
        private int id;
        private String name;
        private String nameKor;
        private String desc;
        private String color;
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
