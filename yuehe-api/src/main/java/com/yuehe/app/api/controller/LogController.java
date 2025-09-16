package com.yuehe.app.api.controller;

import com.yuehe.app.util.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    private static final Logger logger = LoggerFactory.getLogger(LogController.class);

    @PostMapping
    public ResponseEntity<BaseResponse<String>> receiveLog(@RequestBody Map<String, Object> logData) {
        try {
            // 记录前端日志到后端日志文件
            String level = (String) logData.get("level");
            String message = (String) logData.get("message");
            Object data = logData.get("data");
            String userId = (String) logData.get("userId");
            String sessionId = (String) logData.get("sessionId");
            String url = (String) logData.get("url");
            String userAgent = (String) logData.get("userAgent");

            // 构建日志消息
            StringBuilder logMessage = new StringBuilder();
            logMessage.append("[FRONTEND] ");
            if (userId != null) logMessage.append("User:").append(userId).append(" ");
            if (sessionId != null) logMessage.append("Session:").append(sessionId).append(" ");
            if (url != null) logMessage.append("URL:").append(url).append(" ");
            logMessage.append(message);
            if (data != null) logMessage.append(" Data:").append(data.toString());

            // 根据级别记录日志
            switch (level.toLowerCase()) {
                case "error":
                    logger.error(logMessage.toString());
                    break;
                case "warn":
                    logger.warn(logMessage.toString());
                    break;
                case "info":
                    logger.info(logMessage.toString());
                    break;
                case "debug":
                    logger.debug(logMessage.toString());
                    break;
                case "trace":
                    logger.trace(logMessage.toString());
                    break;
                default:
                    logger.info(logMessage.toString());
            }

            return ResponseEntity.ok(BaseResponse.success("Log received successfully"));
        } catch (Exception e) {
            logger.error("Error processing log data", e);
            return ResponseEntity.ok(BaseResponse.error(500, "Failed to process log"));
        }
    }

    @GetMapping("/health")
    public ResponseEntity<BaseResponse<String>> healthCheck() {
        return ResponseEntity.ok(BaseResponse.success("Log service is running"));
    }
}

