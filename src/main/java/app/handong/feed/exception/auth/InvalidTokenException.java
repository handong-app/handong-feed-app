package app.handong.feed.exception.auth;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *  토큰이 정상적이지 않을 때 사용되는 예외처리
 *  HttpStatus 401
 */
@ResponseStatus(value=HttpStatus.UNAUTHORIZED)
@SuppressWarnings("serial")
@NoArgsConstructor
public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(message);
    }
}