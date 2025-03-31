package app.handong.feed.exception.auth;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//2024-07-03 추가(클래스 처음 추가함)

/**
 * 해당 메서드 권한 없을 경우 사용되는 예외처리
 * HttpStatus FORBIDDEN
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
@NoArgsConstructor
public class NoAuthenticatedException extends RuntimeException {
    public NoAuthenticatedException(String message) {
        super(message);
    }
}