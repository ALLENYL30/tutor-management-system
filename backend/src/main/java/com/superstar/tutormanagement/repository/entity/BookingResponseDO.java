package com.superstar.tutormanagement.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 获取用户的所有预约信息
 */
public class BookingResponseDO {
    private int bookId;
    private String tutorName;
    private String studentName;
    private BookingTimeDO time;
    private boolean isComplete;
    private boolean isConfirm;
    private boolean isDelete;
    private Timestamp createTime;
}
