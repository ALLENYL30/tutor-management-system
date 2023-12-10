package com.superstar.tutormanagement.service;

import com.superstar.tutormanagement.controller.request.*;
import com.superstar.tutormanagement.controller.response.CompleteResponse;
import com.superstar.tutormanagement.controller.response.PullProfileResponse;

public interface UpdateService {
    /**
     * 修改个人资料并返回所有信息
     * @param request
     * @return
     */
    PullProfileResponse updateProfile(UpdateProfileRequest request);

    /**
     * 学生选课
     * @param request
     * @return
     */
    Integer studentChooseCourse(ChooseCourseRequest request);

    /**
     * 老师认领课
     * @param request
     * @return
     */
    Integer tutorChooseCourse(ChooseCourseRequest request);

    /**
     * 学生预约老师
     * @param request
     * @return
     */
    Integer studentBookTutor(StudentBookRequest request);

    /**
     * 老师确认预约
     * @param request
     * @return
     */
    Integer tutorConfirmBooking(TutorConfirmBookingRequest request);

    /**
     * 学生确认完成订单
     * @param request
     * @return
     */
    CompleteResponse studentCompleteBooking(StudentCompleteBookingRequest request);

    /**
     * 学生给老师打分
     * @param request
     * @return
     */
    Integer score(ScoreRequest request);

    /**
     * 发送消息
     * @param request
     * @return
     */
    Integer sendMsg(SendNewMsgRequest request);
}
