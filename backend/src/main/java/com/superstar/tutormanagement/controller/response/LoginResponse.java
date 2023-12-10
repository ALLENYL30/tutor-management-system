package com.superstar.tutormanagement.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 登录返回信息
 */
public class LoginResponse {
    private Integer count; // 条数
    private String token;
    private String role; // 身份
}
