package com.superstar.tutormanagement.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 获取老师的已预约时间
 */
public class PullConfirmedRequest {
    private String tutorName;
}
