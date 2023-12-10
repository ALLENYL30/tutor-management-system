package com.superstar.tutormanagement.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 学生确认完成订单
 */
public class StudentCompleteBookingRequest {
    private int bookId;
}
