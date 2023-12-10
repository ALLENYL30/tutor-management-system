package com.superstar.tutormanagement.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 预约时间
 */
public class BookingTimeDO {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
}
