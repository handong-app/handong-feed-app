package app.handong.feed.util;

import java.util.Set;

public class PermissionUtils {
    /**
     * 와일드카드 포함 권한 체크
     * @param granted 유저가 가진 권한들 (ex: "MEMBER:READ", "MEMBER:*", "*:*")
     * @param required 현재 요청에 필요한 권한들
     * @return 모든 required 권한이 granted 권한으로 커버되는지
     */
    public static boolean hasAllRequiredPermissions(Set<String> granted, Set<String> required) {
        return required.stream().allMatch(req -> isGranted(granted, req));
    }

    private static boolean isGranted(Set<String> granted, String required) {
        if (granted.contains(required)) return true;

        String[] parts = required.split(":");
        if (parts.length != 2) return false;

        String group = parts[0];
        String action = parts[1];

        return granted.contains(group + ":*") || granted.contains("*:*");
    }
}
