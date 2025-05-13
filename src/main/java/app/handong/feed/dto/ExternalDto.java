package app.handong.feed.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ExternalDto {
    @Schema
    @Getter
    @Setter
    @NoArgsConstructor
    public static class FeedReqDto {
        Long start, end;
        Integer limit;

        @Schema(description = "신규 피드만 필터링할지 여부")
        boolean isFilterNew;
        @Schema(description = "할당되지 않은 피드만 필터링할지 여부")
        boolean onlyUnassignedFeeds;


        // 커스텀 setter
        public void setIsFilterNew(String value) {
            this.isFilterNew = "1".equals(value);
        }

        // onlyUnassignedFeeds 커스텀 setter
        public void setOnlyUnassignedFeeds(String value) {
            this.onlyUnassignedFeeds = "1".equals(value);
        }
    }
}
