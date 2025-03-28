package app.handong.feed.exception.data;

public class DuplicateTagCodeException extends RuntimeException {

    public DuplicateTagCodeException(String code) {
        super("이미 존재하는 태그 코드입니다: " + code);
    }
}