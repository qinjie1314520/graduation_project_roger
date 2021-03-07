package com.article.config;//package com.article.config;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import org.apache.ibatis.plugin.Interceptor;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.env.Environment;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//import java.io.IOException;
//import java.util.Properties;
//
//@Configuration
//@EnableTransactionManagement
//public class MybatisConfig {
//
//    @Autowired
//    private Environment env;
//
//    @Bean(name = "dataSource")
//    @Primary
//    public DataSource dataSource() {
//        HikariConfig config = new HikariConfig();
//        config.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
//        config.setJdbcUrl(env.getProperty("spring.datasource.url"));
//        config.setUsername(env.getProperty("spring.datasource.username"));
//        config.setPassword(env.getProperty("spring.datasource.password"));
//
//        HikariDataSource ds = new HikariDataSource(config);
//        ds.setIdleTimeout(60000);
//        ds.setConnectionTimeout(60000);
//        ds.setValidationTimeout(3000);
//        //ds.setLoginTimeout(5);
//        ds.setMaxLifetime(60000);
//        return ds;
//
//    }
//
//    @Primary
//    @Bean(name = "sqlSessionFactory")
//    public SqlSessionFactory sqlSessionFactory() throws IOException {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setMapperLocations(new PathMatchingResourcePatternResolver()
//                .getResources("classpath*:mapper/*.xml"));
//        bean.setDataSource(dataSource());
//        Properties prop = new Properties();
//        prop.setProperty("mapUnderscoreToCamelCase", "true");
//
//        bean.setConfigurationProperties(prop);
//        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
//        configuration.setMapUnderscoreToCamelCase(true);
//        bean.setConfiguration(configuration);
//        try {
//            return bean.getObject();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Bean
//    public PlatformTransactionManager dataSourceTransactionManager() {
//        return new DataSourceTransactionManager(dataSource());
//    }
//
//
//
//}
