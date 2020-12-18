//package com.epam.esm.impl.db.config;//package com.epam.esm.db.config;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.env.Environment;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.JpaVendorAdapter;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//import java.util.Properties;
//
//
////@EnableJpaRepositories(basePackages = "com.epam.esm.db")
////@TestConfiguration
//////@ComponentScan(basePackages = "com.epam.esm")
////@PropertySource("datasource-in-memory.properties")
////@EnableTransactionManagement
//@TestConfiguration
//@ComponentScan(value = "com.epam.esm")
//@PropertySource(value = "datasource-in-memory.properties")
//@EnableTransactionManagement
//public class H2JpaConfig {
//
//    private static final Logger LOGGER = LogManager.getLogger(H2JpaConfig.class);
//
//    private Environment environment;
//
//    @Autowired
//    public void setEnvironment(Environment environment) {
//        this.environment = environment;
//    }
//
//    private Properties hibernateProperties() {
//        Properties properties = new Properties();
//        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
//        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
//        return properties;
//    }
//
//    @Bean
//    public DataSource dataSource() {
//        LOGGER.info("Configuring hikari cp");
//        HikariConfig hc = new HikariConfig();
//        hc.setDriverClassName(environment.getRequiredProperty("driver-class-name"));
//        hc.setJdbcUrl(environment.getRequiredProperty("url"));
//        hc.setUsername(environment.getRequiredProperty("username"));
//        hc.setPassword(environment.getRequiredProperty("password"));
//        return new HikariDataSource(hc);
//    }
//
////    @Bean
////    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
////        LocalContainerEntityManagerFactoryBean em
////                = new LocalContainerEntityManagerFactoryBean();
////        em.setDataSource(dataSource());
////        em.setPackagesToScan("com.epam.esm.db.data");
////
////        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
////        em.setJpaVendorAdapter(vendorAdapter);
////        em.setJpaProperties(hibernateProperties());
////
////        return em;
////    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(dataSource());
//        em.setJpaDialect(new HibernateJpaDialect());
//        em.setPackagesToScan("com.epam.esm.db.data");
//        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter(); // JPA implementation
//        em.setJpaVendorAdapter(vendorAdapter);
//        return em;
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager() {
//        JpaTransactionManager transactionManager
//                = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(
//                entityManagerFactory().getObject());
//        return transactionManager;
//    }
//
//
////    @Bean
////    public CommonJpaOperations<User> commonJpaOperations(){
////        return new CommonJpaOperations<>();
////    }
////
////   @Bean
////    public GiftCertificateDAO giftCertificateDAO(){
////        return new GiftCertificateDAOImpl(dataSource());
////   }
////
////   @Bean
////   public UserDAO userDAO(){
////        return new UserDAOImpl(commonJpaOperations());
////   }
//
////    @Bean
////    public PlatformTransactionManager hibernateTransactionManager() {
////        JpaTransactionManager transactionManager = new JpaTransactionManager();
////        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
////        return transactionManager;
////    }
////
////    @Bean
////    public PlatformTransactionManager transactionManager(){
////        JpaTransactionManager transactionManager
////                = new JpaTransactionManager();
////        transactionManager.setEntityManagerFactory(
////                entityManagerFactory().getObject() );
////        return transactionManager;
////    }
//
////    @Bean
////    public LocalSessionFactoryBean sessionFactory() {
////        LOGGER.info("create session factory");
////        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
////        sessionFactory.setDataSource(dataSource());
////        sessionFactory.setPackagesToScan("classpath:com.epam.esm");
////        sessionFactory.setHibernateProperties(hibernateProperties());
////        return sessionFactory;
////    }
//
////    @Bean
////    public HibernateTransactionManager transactionManager() {
////        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
////        transactionManager.setSessionFactory(sessionFactory().getObject());
////        return transactionManager;
////    }
//}
//
