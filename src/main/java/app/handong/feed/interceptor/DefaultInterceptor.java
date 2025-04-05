package app.handong.feed.interceptor;


import app.handong.feed.exception.auth.NoAuthenticatedException;
import app.handong.feed.repository.TbUserPermRepository;
import app.handong.feed.security.annotation.RequiredUserScopes;
import app.handong.feed.security.enums.UserScope;
import app.handong.feed.util.PermissionUtils;
import app.handong.feed.util.TokenFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class DefaultInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TbUserPermRepository tbUserPermRepository;

    public DefaultInterceptor(TbUserPermRepository tbUserPermRepository) {
        this.tbUserPermRepository = tbUserPermRepository;
    }

    //컨트롤러 진입 전에 호출되는 메서드
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getHeader("Authorization") != null) {
            TokenFactory tokenFactory = new TokenFactory();
            String reqUserId = tokenFactory.verifyToken(request.getHeader("Authorization").replaceAll("Bearer ", ""));
            request.setAttribute("reqUserId", reqUserId);

            // 만약에 추가적인 권한이 필요하다면 권한 확인

            // @RequiredUserScopes 검사
            // 현재 요청이 Controller 메서드에 매핑되어 있는지 검사, 맞으면 HandlerMethod로 캐스팅
            if (handler instanceof HandlerMethod handlerMethod) {
                // 컨트롤러 메서드에 붙은 @RequiredUserScopes 애노테이션을 꺼냄
                RequiredUserScopes annotation = handlerMethod.getMethodAnnotation(RequiredUserScopes.class);
                if (annotation != null) {
                    // 일단 디비에서 유저 권한 가져오기
                    Set<String> userPerms = tbUserPermRepository.findAllByIdUserId(reqUserId).stream().map(perm -> perm.getId().getPermission()).collect(Collectors.toSet());

                    // 애노테이션에 명시된 스코프들을 List<String>으로 변환
                    Set<String> requiredScopes = Arrays.stream(annotation.value()).map(scope -> new UserScope.Scope(scope.group(), scope.action()).toString()).collect(Collectors.toSet());

                    // Helper function 을 사용하여 권한이 있는지 확인.
                    if (!PermissionUtils.hasAllRequiredPermissions(userPerms, requiredScopes)) {
                        response.setStatus(HttpStatus.FORBIDDEN.value());
                        return false;
                    }
                }
            }
        } else {
            throw new NoAuthenticatedException("Not Authenticated User");
        }
        //logger.info("preHandle / refreshToken [{}]", response.getHeader("refreshToken"));

        String requestURI = request.getRequestURI();
        String requestMethod = request.getMethod();
        String ipAddr = request.getHeader("X-Forwarded-For");
        if (ipAddr == null || ipAddr.isEmpty()) {
            ipAddr = request.getRemoteAddr();
        }
        logger.info("preHandle [{} {}] : {} / {}", requestMethod, requestURI, ipAddr, request.getAttribute("reqUserId"));
        return true;
    }

    //컨트롤러 실행 후에 호출되는 메서드
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        logger.info("postHandle / request [{}]", request);
    }

    //모든것을 마친 후 실행되는 메서드
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
