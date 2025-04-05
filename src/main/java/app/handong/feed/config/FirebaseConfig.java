package app.handong.feed.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {
    @Value("${FB_CONFIG_PATH:${FB_CONFIG_PATH_DEFAULT:#{null}}}")
    private String firebaseConfigPath;

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
        // Check if running in development or production
        boolean isProduction = System.getenv("ENV") != null && System.getenv("ENV").equalsIgnoreCase("PRODUCTION");

        if (isProduction) {
            // Use absolute path in production
            return new FileInputStream(firebaseConfigPath);
        } else {
            // Use ClassPathResource in development
            return new ClassPathResource(firebaseConfigPath).getInputStream();
        }
    }
}
