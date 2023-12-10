package com.superstar.tutormanagement.controller.response;

import com.superstar.tutormanagement.repository.entity.BookingTimeDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 获取老师的已预约时间
 */
public class PullConfirmedResponse {
    private List<BookingTimeDO> list;
}
