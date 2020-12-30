package com.epam.esm.service;

import com.epam.esm.impl.config.HibernateConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.epam.esm")
@Import(HibernateConfig.class)
public class ServiceConfig {
}
