package com.yuehe.app.api.controller;

import com.yuehe.app.entity.User;
import com.yuehe.app.service.UserService;
import com.yuehe.app.util.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<Map<String, Object>>> login(@RequestBody LoginRequest request) {
        try {
            // 验证用户名和密码
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            // 获取用户信息
            User user = userService.findByUsername(request.getUsername());
            if (user == null) {
                return ResponseEntity.ok(BaseResponse.error("用户不存在"));
            }

            // 生成token (这里简化处理，实际应该使用JWT)
            String token = generateToken(user);

            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            data.put("user", user);

            return ResponseEntity.ok(BaseResponse.success(data));
        } catch (Exception e) {
            return ResponseEntity.ok(BaseResponse.error("用户名或密码错误"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<BaseResponse<String>> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(BaseResponse.success("退出成功"));
    }

    @GetMapping("/me")
    public ResponseEntity<BaseResponse<User>> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userService.findByUsername(username);
            if (user != null) {
                return ResponseEntity.ok(BaseResponse.success(user));
            }
        }
        return ResponseEntity.ok(BaseResponse.error("未登录"));
    }

    private String generateToken(User user) {
        // 这里应该使用JWT生成token，暂时返回简单的字符串
        return "token_" + user.getId() + "_" + System.currentTimeMillis();
    }

    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
