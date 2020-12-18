package com.epam.esm.impl.db.service.impl;

import com.epam.esm.impl.db.config.HibernateConfig;
import com.epam.esm.impl.db.service.InMemoryDatabaseUtil;
import com.epam.esm.service.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.test.context.ActiveProfiles;

//@ExtendWith(SpringExtension.class)
//@SpringBootTest(classes = {UserDAOImpl.class, H2JpaConfig.class})
//@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {H2JpaConfig.class, TestConfigs.class
//})

//@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
//@EnableConfigurationProperties(H2JpaConfig.class)
//@DataJpaTest
//@SpringBootTest
//@ContextConfiguration(classes = {H2JpaConfig.class, TestConfigs.class})
//@ActiveProfiles("test")
//@ComponentScan(excludeFilters  = {@ComponentScan.Filter(
//        type = FilterType.ASSIGNABLE_TYPE, classes = {HibernateConfig.class})})
@EnableAutoConfiguration(exclude=HibernateConfig.class)
class UserDAOImplTest {

    private static final Logger LOGGER = LogManager.getLogger(UserDAOImplTest.class);
    private InMemoryDatabaseUtil inMemoryDatabaseUtil = new InMemoryDatabaseUtil();
    private EmbeddedDatabase embeddedDatabase;

    private UserDAO userDAO;
//
//    UserDAOImplTest(UserDAO userDAO) {
//        this.userDAO = userDAO;
//    }

//    public UserDAOImplTest(UserDAO userDAO) {
//        this.userDAO = userDAO;
//    }

    @BeforeEach
    void setUp() {
        ApplicationContext context = new AnnotationConfigApplicationContext(HibernateConfig.class);
        userDAO = context.getBean(UserDAO.class);
        inMemoryDatabaseUtil.setUpH2Db(embeddedDatabase);
    }

    @AfterEach
    void shutdown() {
        embeddedDatabase.shutdown();
    }


    @Test
    void userDaoNotNull() {
        Assertions.assertNotNull(userDAO);
    }

    @Test
    void findAll() {
        int expectedSize = 10;
        int actualSize = userDAO.findAll(10L,1L).size();
        Assertions.assertEquals(expectedSize,actualSize);
    }

    @Test
    void createEntity() {
    }

    @Test
    void findById() {
    }

    @Test
    void updateEntity() {
    }

    @Test
    void deleteEntity() {
    }

    @org.springframework.context.annotation.Configuration
    public static class ContextConfiguration {
    }
}