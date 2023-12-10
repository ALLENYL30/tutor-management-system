package com.superstar.tutormanagement.controller.response;

import com.superstar.tutormanagement.repository.entity.UnconfirmedResponseDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 获取带确认预约
 */
public class PullUnconfirmedResponse {
    List<UnconfirmedResponseDO> list;
}
