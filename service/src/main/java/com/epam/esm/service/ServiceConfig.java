package com.epam.esm.service;

import com.epam.esm.impl.db.config.HibernateConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan("com.epam.esm")
@Import(HibernateConfig.class)
public class ServiceConfig {
}
