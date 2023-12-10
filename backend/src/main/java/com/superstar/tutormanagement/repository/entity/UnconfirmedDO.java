package com.superstar.tutormanagement.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 未确认预约实体
 */
public class UnconfirmedDO {
    private int id;
    private int studentId;
    private String userName;
    private Timestamp startTime;
    private Timestamp createTime;
}
