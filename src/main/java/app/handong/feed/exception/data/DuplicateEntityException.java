package app.handong.feed.exception.data;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * 중복 엔티티 발생한 경우 사용되는 예외처리
 * HttpStatus CONFLICT
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
@SuppressWarnings("serial")
@NoArgsConstructor
public class DuplicateEntityException extends RuntimeException {
    public DuplicateEntityException(String message) {
        super(message);
    }
}