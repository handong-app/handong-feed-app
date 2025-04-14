package app.handong.feed.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

public class TbmessageDto {
    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Detail {
        @Schema(description = "title", example = "")
        private int subjectId;
        private String id;
        private int sentAt;
        private String message;
        private List<String> files;
        private boolean like;
        private int messageCount;
        private List<TagDto.ReadUserResDto> tags;
    }

    @Schema
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Count {
        @Schema(description = "title", example = "")
        private int count;
    }

    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FileDetail {
        @Schema(description = "title", example = "")
        private String hash;
        private String ext;
    }

    @Schema
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ShareHash {
        @Schema(description = "shortHash", example = "")
        private String shortHash;
        private String hash;
    }
}
