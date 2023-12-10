package com.superstar.tutormanagement.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 学生用户信息
 */
public class StudentInfoDO {
    private String account;
    private String userName;
    private String address;
    private String phone;
    private String timeZone;
    private InputStream image;
}
