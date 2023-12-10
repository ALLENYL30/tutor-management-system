package com.superstar.tutormanagement.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 学生确认订单完成
 */
public class CompleteResponse {
    private int bookId;
}
