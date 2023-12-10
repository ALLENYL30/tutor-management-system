package com.superstar.tutormanagement.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 学生预约老师时间
 */
public class StudentBookRequest {
    private String tutorName;
    private String time;
}
