package com.superstar.tutormanagement.controller.response;

import com.superstar.tutormanagement.repository.entity.TutorResponseDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 老师信息给前端
 */
public class PullTutorResponse {
    List<TutorResponseDO> list;
}
