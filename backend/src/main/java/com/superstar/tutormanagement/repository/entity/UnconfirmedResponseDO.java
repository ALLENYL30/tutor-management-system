package com.superstar.tutormanagement.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 待确认预约
 */
public class UnconfirmedResponseDO {
    private int id; // 订单id
    private int studentId;
    private String studentName;
    private BookingTimeDO bookingTimeDO;
    private Timestamp createTime;
}
