package com.yuehe.app.api.controller;

import com.yuehe.app.entity.User;
import com.yuehe.app.service.UserService;
import com.yuehe.app.util.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*")
public class TestDataController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/init-users")
    public ResponseEntity<BaseResponse<String>> initTestUsers() {
        try {
            // 创建测试用户
            User admin = new User();
            admin.setId("yh001");
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            userService.create(admin);

            User expert = new User();
            expert.setId("yh002");
            expert.setUsername("expert");
            expert.setPassword(passwordEncoder.encode("expert123"));
            expert.setRole("EXPERT");
            userService.create(expert);

            User operator = new User();
            operator.setId("yh003");
            operator.setUsername("operator");
            operator.setPassword(passwordEncoder.encode("operator123"));
            operator.setRole("OPERATOR");
            userService.create(operator);

            return ResponseEntity.ok(BaseResponse.success("测试用户创建成功"));
        } catch (Exception e) {
            return ResponseEntity.ok(BaseResponse.error("创建测试用户失败: " + e.getMessage()));
        }
    }
}
