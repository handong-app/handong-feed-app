package app.handong.feed.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

@Configuration
public class FirebaseConfig {
    @Value("${FB_CONFIG_BASE64:${FB_CONFIG_BASE64_DEFAULT:#{null}}}")
    private String firebaseConfigBase64;

    @Value("${FB_BUCKET:${FB_BUCKET_DEFAULT:#{null}}}")
    private String storageBucketUrl;

    @Bean
    public FirebaseApp firebaseApp() throws Exception {

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(loadFirebaseConfig()))
                    .setStorageBucket(storageBucketUrl)
                    .build();

            return FirebaseApp.initializeApp(options);
        } else {
            return FirebaseApp.getInstance();
        }
    }

    private InputStream loadFirebaseConfig() throws Exception {
        if (firebaseConfigBase64 == null || firebaseConfigBase64.isEmpty()) {
            throw new IllegalStateException("환경변수 FIREBASE_CONFIG_BASE64가 설정되지 않았습니다.");
        }

        byte[] decodedBytes = Base64.getDecoder().decode(firebaseConfigBase64);
        return new ByteArrayInputStream(decodedBytes);
    }
}
