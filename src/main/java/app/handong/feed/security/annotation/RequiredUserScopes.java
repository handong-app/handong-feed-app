package app.handong.feed.security.annotation;


import app.handong.feed.security.enums.UserScope;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented //JavaDoc 에 이 애노테이션이 붙은걸 보이게 해줌
public @interface RequiredUserScopes {
    /**
     * 외부 API 의 Scope (권한 범위) 를 나타내는 애노테이션입니다.
     * e.g.,  @RequiredUserScopes({
     *             @RequiredUserScopes.Scope(group = UserScope.ScopeGroup.MEMBER, action = UserScope.ScopeAction.READ)
     *     })
     */
    Scope[] value(); // 여러 개 스코프 지원 위해 배열로 선언

    @interface Scope {
        UserScope.ScopeGroup group();
        UserScope.ScopeAction action();
    }
}