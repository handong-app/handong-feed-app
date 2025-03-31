package app.handong.feed.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import app.handong.feed.exception.auth.NoAuthorizationException;

import java.util.Collections;


@RequestMapping("/tbuser")
@Controller
public class TbuserPageController {

    @PostMapping("/login/google")  // POST 요청을 처리할 메서드 추가
    public String loginPost(@RequestParam String credential, Model model) {
        return login(credential, model);  // GET 요청과 같은 로직을 사용
    }

    @GetMapping("/{page}")
    public String page(@PathVariable("page") String page){
        return "tbuser/" + page;
    }

    @GetMapping("/login/google")
    public String login(@RequestParam String credential, Model model){
        HttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new GsonFactory();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList("542164017545-c6tsebe44kpkpk43b7u8njouir63oqq8.apps.googleusercontent.com"))
                .build();

        GoogleIdToken idToken = null;
        boolean isValidUser = false;  // 초기값을 false로 설정

        try {
            idToken = verifier.verify(credential);
        } catch(Exception e) {
            e.printStackTrace();
        }

        if (idToken != null) {
            Payload payload = idToken.getPayload();
            String username = payload.get("email") + "";

            if (username.trim().endsWith("@handong.ac.kr")) {
                isValidUser = true;
            } else {
                throw new NoAuthorizationException("Unauthorized user");
            }

            model.addAttribute("token", username);
        } else {
            System.out.println("Invalid ID token.");
        }

        model.addAttribute("isValidUser", isValidUser);  // 모델에 isValidUser 값 추가
        return "index";  // index.html로 리다이렉트
    }


}
