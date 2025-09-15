package com.yuehe.app.util;

import java.time.LocalDateTime;

public class BaseResponse<T> {
    private int code;
    private String message;
    private T data;
    private String timestamp;

    public BaseResponse() {
        this.timestamp = LocalDateTime.now().toString();
    }

    public BaseResponse(int code, String message, T data) {
        this();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(200, "操作成功", data);
    }

    public static <T> BaseResponse<T> success(String message, T data) {
        return new BaseResponse<>(200, message, data);
    }

    public static <T> BaseResponse<T> error(String message) {
        return new BaseResponse<>(500, message, null);
    }

    public static <T> BaseResponse<T> error(int code, String message) {
        return new BaseResponse<>(code, message, null);
    }

    // Getters and setters
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}