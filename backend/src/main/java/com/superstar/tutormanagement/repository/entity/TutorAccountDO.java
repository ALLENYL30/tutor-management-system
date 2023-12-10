package com.superstar.tutormanagement.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 老师账号信息
 */
public class TutorAccountDO {
    private String account;
    private String passwordMd5;
    private String userName;
}
