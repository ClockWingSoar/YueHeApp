package com.yuehe.app.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.yuehe.app"})
public class YueHeApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(YueHeApiApplication.class, args);
    }
}

