package com.superstar.tutormanagement.controller.response;

import com.superstar.tutormanagement.repository.entity.BookingResponseDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 获取用户的所有预约信息
 */
public class PullBookingResponse {
    List<BookingResponseDO> list;
}
