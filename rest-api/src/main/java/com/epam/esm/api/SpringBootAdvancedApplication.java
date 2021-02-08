package com.epam.esm.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.epam.esm")
public class SpringBootAdvancedApplication  {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootAdvancedApplication.class, args);
    }
}
