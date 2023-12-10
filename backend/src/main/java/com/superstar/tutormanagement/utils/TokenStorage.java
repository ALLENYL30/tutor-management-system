package com.superstar.tutormanagement.utils;

import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * token存储库
 */
@Component
public class TokenStorage {
    private ThreadLocal<String> tokenHolder = new ThreadLocal<>();

    public String getToken() {
        return tokenHolder.get();
    }

    public void setToken(String token) {
        if (!Objects.equals(token, tokenHolder.get())) {
            tokenHolder.set(token);
        }
    }

    public void clearToken() {
        tokenHolder.remove();
    }
}
