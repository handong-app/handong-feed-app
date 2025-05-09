package app.handong.feed.config;


import app.handong.feed.interceptor.ApiKeyAuthInterceptor;
import app.handong.feed.interceptor.DefaultInterceptor;
import app.handong.feed.repository.ApiKeyRepository;
import app.handong.feed.repository.TbUserPermRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final ApiKeyRepository apiKeyRepository;
    private final CustomProperties customProperties;
    private final TbUserPermRepository tbUserPermRepository;

    public WebMvcConfig(ApiKeyRepository apiKeyRepository, CustomProperties customProperties, TbUserPermRepository tbUserPermRepository) {
        this.apiKeyRepository = apiKeyRepository;
        this.customProperties = customProperties;
        this.tbUserPermRepository = tbUserPermRepository;
    }

    //인터셉터 설정을 위함
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DefaultInterceptor(tbUserPermRepository))
                .addPathPatterns("/api/**") //인터셉터가 실행되야 하는 url 패턴 설정
                .excludePathPatterns("/api/health", "/resources/**", "/api/tbuser/login/google", "/api/lab/**", "/api/external/**"); //인터셉터가 실행되지 않아야 하는 url 패턴
        registry.addInterceptor(new ApiKeyAuthInterceptor(apiKeyRepository, customProperties))
                .addPathPatterns("/api/external/**"); // 외부 api 요청시 인터셉터 실행
    }

}