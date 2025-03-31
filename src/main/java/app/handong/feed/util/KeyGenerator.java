package app.handong.feed.util;

import java.security.SecureRandom;
import java.util.stream.Collectors;

public class KeyGenerator {

    private static final SecureRandom secureRandom = new SecureRandom();

    public static String generateRandomSuffix(int length) {
        return secureRandom.ints(length, 0, 36)
                .mapToObj(i -> Integer.toString(i, 36))
                .collect(Collectors.joining());
    }
}