package com.epam.esm.api.config;

import com.epam.esm.impl.db.config.HibernateConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan("com.epam.esm.service")
@Import(HibernateConfig.class)
public class RestConfig {
}
