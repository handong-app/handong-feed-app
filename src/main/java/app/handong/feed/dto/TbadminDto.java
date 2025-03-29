package app.handong.feed.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TbadminDto {
    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserDetail {
        private String id;
        private String name;
        private LocalDateTime createdAt;
        private LocalDateTime lastLoginTime;
        private LocalDateTime lastReadTime;

    }

    @Schema
    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ApiKeyCreateReqDto {
        @Schema(description = "API 키 소유자", example = "spotlight")
        private String owner;

        @Schema(description = "허용된 스코프 목록", example = "[\"tag:create\", \"tag:delete\"]")
        private List<String> scopes = new ArrayList<>();

    }

    @Schema
    @Getter @Setter
    @AllArgsConstructor
    public static class ApiKeyCreateRespDto {
        @Schema(description = "API 키 (단 1회 노출)", example = "xXxYyYzZ...")
        private String apiKey;

        @Schema(description = "발급 시간")
        private LocalDateTime issuedAt;
    }
}
