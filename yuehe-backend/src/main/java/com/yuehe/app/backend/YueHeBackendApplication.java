package com.yuehe.app.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.yuehe.app.entity")
@EnableJpaRepositories("com.yuehe.app.repository")
public class YueHeBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(YueHeBackendApplication.class, args);
    }
}
