package com.epam.esm.impl;

import com.epam.esm.service.GiftCertificateDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import static org.junit.jupiter.api.Assertions.*;

class GiftCertificateDAOImplTest {
    private EmbeddedDatabase embeddedDatabase;
    private GiftCertificateDAO giftCertificateDAO;

    @BeforeEach
    void setUp() {

        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addDefaultScripts()
                .setType(EmbeddedDatabaseType.H2)
                .build();
        giftCertificateDAO = new GiftCertificateDAOImpl(embeddedDatabase);

    }

    @AfterEach
    void shutdown() {
        embeddedDatabase.shutdown();
    }


    @Test
    void findAll() {

        int expectedSize = 5;
        Assertions.assertEquals(expectedSize, giftCertificateDAO.findAll().size());

    }

}