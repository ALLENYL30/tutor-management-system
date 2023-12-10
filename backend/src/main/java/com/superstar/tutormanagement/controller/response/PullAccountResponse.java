package com.superstar.tutormanagement.controller.response;

import com.superstar.tutormanagement.repository.entity.TutorAccountDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * admin获取老师的账号信息
 */
public class PullAccountResponse {
    List<TutorAccountDO> tutorAccountDOList;
}
