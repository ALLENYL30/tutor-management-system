package com.superstar.tutormanagement.service;

import com.superstar.tutormanagement.controller.request.PullChatRequest;
import com.superstar.tutormanagement.controller.request.PullConfirmedRequest;
import com.superstar.tutormanagement.controller.request.PullTutorByNameRequest;
import com.superstar.tutormanagement.controller.response.*;

public interface PullService {
    /**
     * 获取图片url
     * @return
     */
    PullImageResponse pullImage();

    /**
     * 获取老师的账户信息
     * @return
     */
    PullAccountResponse pullTutorAccount();

    /**
     * 获取个人资料
     * @return
     */
    PullProfileResponse pullProfile();

    /**
     * admin获取课程信息
     * @return
     */
    PullCoursesResponse pullCourses();

    /**
     * 不用登录获取所有课程
     * @return
     */
    PullCourseInfoResponse pullCourseInfo();

    /**
     * 学生已选课程
     * @return
     */
    PullCommonCourseResponse pullCommonCourse();

    /**
     * 获取待确认预约
     * @return
     */
    PullUnconfirmedResponse pullUnconfirmed();

    /**
     * 获取已预约时间
     * @return
     * @param request
     */
    PullConfirmedResponse pullConfirmed(PullConfirmedRequest request);

    /**
     * 获取用户的预约信息
     * @return
     */
    PullBookingResponse pullBookings();

    /**
     * 获取所有老师信息，并排序
     * @return
     */
    PullTutorResponse pullTutors();

    /**
     * admin获取所有学生的信息
     * @return
     */
    PullStudentResponse pullStudents();

    /**
     * 通过名字获取老师的信息
     * @param request
     * @return
     */
    PullTutorResponse pullTutorByName(PullTutorByNameRequest request);

    /**
     * 获取聊天用户
     * @return
     */
    PullChatListResponse pullChatList();

    /**
     * 获取历史聊天记录
     * @param request
     * @return
     */
    PullChatInfoResponse pullChatInfo(PullChatRequest request);

    /**
     * 获取最新数据
     * @return
     */
    PullChatInfoResponse pullNewMsg();

    /**
     * 获取所有课程带token
     * @return
     */
    PullCourseInfoWithTokenResponse pullCourseInfoWithToken();
}
