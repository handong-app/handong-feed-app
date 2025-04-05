package app.handong.feed.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class GoogleAuthService {

    private final GoogleIdTokenVerifier verifier;


    public GoogleAuthService() { // 구글 토큰을 받기 위한 코드
        this.verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList("542164017545-c6tsebe44kpkpk43b7u8njouir63oqq8.apps.googleusercontent.com"))
                .build();
    }

    public String[] verifyGoogleToken(String credential) {
        try { // 받은 구글 토큰을 검증하고 이메일을 추출하는 코드
            GoogleIdToken idToken = verifier.verify(credential);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();
                return new String[]{payload.getEmail(), payload.get("name").toString()};
            } else {
                throw new RuntimeException("Invalid ID token");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to verify ID token", e);
        }
    }

    public String verifyGoogleName(String credential) {
        try {
            // 받은 구글 토큰을 검증하고 페이로드를 추출하는 코드
            GoogleIdToken idToken = verifier.verify(credential);
            if (idToken != null) {
                // 페이로드 추출
                GoogleIdToken.Payload payload = idToken.getPayload();

                // "name" 필드 추출
                String name = (String) payload.get("name");

                if (name != null) {
                    return name;
                } else {
                    throw new RuntimeException("Name not found in token");
                }
            } else {
                throw new RuntimeException("Invalid ID token");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to verify ID token", e);
        }
    }


    /*
    * private final GoogleIdTokenVerifier verifier;
    private final TbuserService tbuserService;
    private final JwtTokenUtil jwtTokenUtil;

    public GoogleAuthService(TbuserService tbuserService, JwtTokenUtil jwtTokenUtil) {
        this.tbuserService = tbuserService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList("your_client_id_here"))
                .build();
    }

    public GoogleLoginResponse verifyAndLogin(GoogleLoginRequest request) {
        GoogleIdToken idToken = verifier.verify(request.getCredential());
        if (idToken == null) {
            throw new InvalidTokenException("Invalid Google ID Token");
        }

        String email = idToken.getPayload().getEmail();
        if (!email.endsWith("@handong.ac.kr")) {
            throw new NoAuthorizationException("Unauthorized domain");
        }

        // 사용자가 존재하는지 확인하고, 없으면 새로 등록
        Tbuser user = tbuserService.findOrCreateUserByEmail(email);

        // JWT 토큰 생성
        String token = jwtTokenUtil.generateToken(user.getUsername());

        return new GoogleLoginResponse(token);
    }
    * */
}
