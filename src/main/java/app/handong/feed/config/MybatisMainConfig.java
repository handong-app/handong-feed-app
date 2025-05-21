// 출처: https://jangjjolkit.tistory.com/55

package app.handong.feed.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


/**
 * mainDataSource 빈은 DataSourceConfig에서 정의되며,
 * JPA, MyBatis 등에서 공통으로 주입받아 사용합니다.
 */
@Configuration
@MapperScan(
        basePackages = "app.handong.feed.mapper.main",
        sqlSessionFactoryRef = "mainSqlSessionFactory"
)
public class MybatisMainConfig {

    @Primary
    @Bean
    public SqlSessionFactory mainSqlSessionFactory(@Qualifier("mainDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:/mapper/main/*.xml"));
        return factory.getObject();
    }

    @Bean
    public DataSourceTransactionManager mainTransactionManager(@Qualifier("mainDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
