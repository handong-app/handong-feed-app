package app.handong.feed.exception.data;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//2024-07-03 추가(클래스 처음 추가함)
/**
 *  찾으려고 하는 Data가 없을 때 사용되는 예외처리
 *  HttpStatus 404
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND)
@SuppressWarnings("serial")
@NoArgsConstructor
public class NoMatchingDataException extends RuntimeException {
    public NoMatchingDataException(String message) {
        super(message);
    }
}