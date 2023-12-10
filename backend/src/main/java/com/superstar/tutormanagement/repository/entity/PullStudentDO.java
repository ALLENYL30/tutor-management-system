package com.superstar.tutormanagement.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 学生账号实体
 */
public class PullStudentDO {
    private String account;
    private String passwordMd5;
    private String userName;
    private String address;
    private String timeZone;
    private boolean isDelete;
}
