package com.superstar.tutormanagement.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * admin删除课程
 */
public class DeleteCourseRequest {
    private String name;
    private Integer type; // 0: 逻辑删除，1: 物理删除
}
