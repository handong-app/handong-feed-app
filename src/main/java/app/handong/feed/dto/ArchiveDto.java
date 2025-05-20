package app.handong.feed.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ArchiveDto {
    @Schema
    @Getter
    @Setter
    @NoArgsConstructor
    public static class NewsletterDto {
        private int id;
        private LocalDate date;
        private String data;
        private LocalDateTime createdAt;
    }

}
