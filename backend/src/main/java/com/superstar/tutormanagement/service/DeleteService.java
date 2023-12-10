package com.superstar.tutormanagement.service;

import com.superstar.tutormanagement.controller.request.DeleteBookRequest;
import com.superstar.tutormanagement.controller.request.DeleteCourseRequest;
import com.superstar.tutormanagement.controller.request.DeleteSelectCourseRequest;

public interface DeleteService {
    /**
     * admin根据type删除课程
     * @param request
     * @return
     */
    Integer deleteCourse(DeleteCourseRequest request);
    /**
     * 删除学生课程
     */
    Integer deleteStudentCourse(DeleteSelectCourseRequest request);

    /**
     * 删除老师选课
     * @param request
     * @return
     */
    Integer deleteTutorCourse(DeleteSelectCourseRequest request);

    /**
     * 取消预约
     * @return
     * @param request
     */
    Integer deleteBook(DeleteBookRequest request);
}
