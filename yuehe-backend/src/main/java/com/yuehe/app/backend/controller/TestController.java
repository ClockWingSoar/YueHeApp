package com.yuehe.app.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/api/backend")
@CrossOrigin(origins = "*")
public class TestController {
    
    @GetMapping("/test")
    public String test() {
        return "yuehe-backend is running!";
    }
    
    @GetMapping("/health")
    public String health() {
        return "yuehe-backend is healthy!";
    }
}
