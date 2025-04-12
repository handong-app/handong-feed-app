package app.handong.feed.util;

import app.handong.feed.exception.auth.NoAuthorizationException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public class RequestUtils {
    private RequestUtils() {}
    
    public static String getReqUserId(HttpServletRequest request) {
        return Optional.ofNullable(request.getAttribute("reqUserId"))
                .map(Object::toString)
                .orElseThrow(() -> new NoAuthorizationException("Missing user ID"));
    }

    public static String getReqApiId(HttpServletRequest request) {
        return Optional.ofNullable(request.getAttribute("reqApiId"))
                .map(Object::toString)
                .orElseThrow(() -> new NoAuthorizationException("Missing API ID"));
    }
}