package app.handong.feed.config;


import app.handong.feed.interceptor.ApiKeyAuthInterceptor;
import app.handong.feed.interceptor.DefaultInterceptor;
import app.handong.feed.repository.ApiKeyRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final ApiKeyRepository apiKeyRepository;
    private final CustomProperties customProperties;

    /**
     * Constructs a new WebMvcConfig instance with the injected API key repository and custom properties.
     *
     * <p>The API key repository manages API keys while the custom properties supply configuration settings
     * used during interceptor registration.
     */
    public WebMvcConfig(ApiKeyRepository apiKeyRepository, CustomProperties customProperties) {
        this.apiKeyRepository = apiKeyRepository;
        this.customProperties = customProperties;
    }

    /**
     * Configures interceptors for processing HTTP requests.
     *
     * <p>This method registers two interceptors:
     * <ul>
     *   <li>
     *     A <code>DefaultInterceptor</code> for handling all requests matching <code>/api/**</code>,
     *     excluding those matching <code>/resources/**</code>, <code>/api/tbuser/login/google</code>,
     *     <code>/api/lab/**</code>, and <code>/api/external/**</code>.
     *   </li>
     *   <li>
     *     An <code>ApiKeyAuthInterceptor</code> for validating API key authentication on requests
     *     matching <code>/api/external/**</code>.
     *   </li>
     * </ul>
     * </p>
     *
     * @param registry the registry to which the interceptors are added
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DefaultInterceptor())
                .addPathPatterns("/api/**") //인터셉터가 실행되야 하는 url 패턴 설정
                .excludePathPatterns("/resources/**", "/api/tbuser/login/google", "/api/lab/**", "/api/external/**"); //인터셉터가 실행되지 않아야 하는 url 패턴
        registry.addInterceptor(new ApiKeyAuthInterceptor(apiKeyRepository, customProperties))
                .addPathPatterns("/api/external/**"); // 외부 api 요청시 인터셉터 실행
    }

}