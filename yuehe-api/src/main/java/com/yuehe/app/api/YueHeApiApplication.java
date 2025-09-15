package com.yuehe.app.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.yuehe.app"})
@EnableJpaRepositories(basePackages = {"com.yuehe.app.repository"})
@EntityScan(basePackages = {"com.yuehe.app.entity"})
public class YueHeApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(YueHeApiApplication.class, args);
    }
}

