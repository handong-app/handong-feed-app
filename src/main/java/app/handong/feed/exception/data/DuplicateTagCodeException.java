package app.handong.feed.exception.data;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 태그 생성시 중복 태그코드 (태그의 PK) 발생한 경우 사용되는 예외처리
 * HttpStatus CONFLICT
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
@SuppressWarnings("serial")
@NoArgsConstructor
public class DuplicateTagCodeException extends RuntimeException {
    public DuplicateTagCodeException(String code) {
        super("이미 존재하는 태그 코드입니다: " + code);
    }
}