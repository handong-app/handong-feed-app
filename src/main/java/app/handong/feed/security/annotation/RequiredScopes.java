package app.handong.feed.security.annotation;


import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented //JavaDoc 에 이 애노테이션이 붙은걸 보이게 해줌
public @interface RequiredScopes {
    /**
     * 외부 API 의 Scope (권한 범위) 를 나타내는 애노테이션입니다.
     * @return
     */
    String[] value(); // 여러 개 스코프 지원 위해 배열로 선언
}