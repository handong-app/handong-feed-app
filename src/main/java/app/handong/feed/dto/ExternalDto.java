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

        boolean isFilterNew;
        boolean onlyUnassignedFeeds;


        // 커스텀 setter
        public void setIsFilterNew(String value) {
            this.isFilterNew = "1".equals(value);
        }
    }
}
