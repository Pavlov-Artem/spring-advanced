package com.epam.esm.impl.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "com.epam.esm")
@EnableTransactionManagement
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class})
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
public class HibernateConfig {

    private static final Logger LOGGER = LogManager.getLogger(HibernateConfig.class);

    private Environment environment;

    public HibernateConfig(Environment environment) {
        this.environment = environment;
    }


    private Properties hibernateProperties() {
        Properties properties = new Properties();
        // properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("spring.jpa.show-sql"));
        return properties;
    }

    @Bean
    @Profile("prod")
    public DataSource dataSource() {
        HikariConfig hc = new HikariConfig();
        hc.setDriverClassName(environment.getRequiredProperty("spring.datasource.driver-class-name"));
        hc.setJdbcUrl(environment.getRequiredProperty("spring.datasource.url"));
        hc.setUsername(environment.getRequiredProperty("spring.datasource.username"));
        hc.setPassword(environment.getRequiredProperty("spring.datasource.password"));
        return new HikariDataSource(hc);
    }

//    @Bean
//    @Profile("test")
//    @ConfigurationProperties(value = "application-test.properties")
//    public DataSource testDataSource() {
////        LOGGER.info("Configuring hikari cp");
////        LOGGER.info("props driver" + environment.getRequiredProperty("driver-class-name"));
////        LOGGER.info("props url" + environment.getRequiredProperty("url"));
////        LOGGER.info("props user" + environment.getRequiredProperty("username"));
////        HikariConfig hc = new HikariConfig();
//////        hc.setDriverClassName(environment.getRequiredProperty("spring.datasource.driver-class-name"));
//////        hc.setJdbcUrl(environment.getRequiredProperty("spring.datasource.url"));
//////        hc.setUsername(environment.getRequiredProperty("spring.datasource.username"));
//////        hc.setPassword(environment.getRequiredProperty("spring.datasource.password"));
//        EmbeddedDatabase embeddedDatabase = new EmbeddedDatabaseBuilder()
//                .addDefaultScripts()
//                .setType(EmbeddedDatabaseType.H2)
//                .build();
////        LOGGER.info(hc.getUsername());
//        dataSource = embeddedDatabase;
//        return embeddedDatabase;
//    }


//    public DataSource dataSourceTest(){
//        EmbeddedDatabase
//    }
//    @Bean
//    public DataSource dataSourceTest() {
//        LOGGER.info("Configuring hikari cp");
//        LOGGER.info("props driver" + environment.getRequiredProperty("driver-class-name"));
//        LOGGER.info("props url" + environment.getRequiredProperty("url"));
//        LOGGER.info("props user" + environment.getRequiredProperty("username"));
//        HikariConfig hc = new HikariConfig();
//        hc.setDriverClassName(environment.getRequiredProperty("driver-class-name"));
//        hc.setJdbcUrl(environment.getRequiredProperty("url"));
//        hc.setUsername(environment.getRequiredProperty("username"));
//        hc.setPassword(environment.getRequiredProperty("password"));
//        LOGGER.info(hc.getUsername());
//        return new HikariDataSource(hc);
//    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(this.dataSource());
        em.setJpaDialect(new HibernateJpaDialect());
        em.setPackagesToScan("com.epam.esm.data");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter(); // JPA implementation
        em.setJpaProperties(hibernateProperties());
        em.setJpaVendorAdapter(vendorAdapter);
        LOGGER.info(vendorAdapter.getJpaDialect());
        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                entityManagerFactory().getObject());
        return transactionManager;
    }

//    @Bean
//    public LocalSessionFactoryBean sessionFactory() {
//        LOGGER.info("create session factory");
//        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//        sessionFactory.setDataSource(dataSource());
//        sessionFactory.setPackagesToScan("classpath:com.epam.esm");
//        sessionFactory.setHibernateProperties(hibernateProperties());
//        return sessionFactory;
//    }

//    @Bean
//    public HibernateTransactionManager transactionManager() {
//        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
//        transactionManager.setSessionFactory(entityManagerFactory().ge);
//        return transactionManager;
//    }
}

