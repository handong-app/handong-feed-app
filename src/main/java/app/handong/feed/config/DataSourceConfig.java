package app.handong.feed.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * JPA, MyBatis 등에서 공통으로 사용하는 DataSource 빈을 정의합니다.
 */
@Configuration
public class DataSourceConfig {
    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSource mainDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }
}
