package com.superstar.tutormanagement.service;

import com.superstar.tutormanagement.controller.request.StudentRegisterRequest;
import com.superstar.tutormanagement.controller.request.TutorRegisterRequest;

public interface RegisterService {
    /**
     * 学生注册
     * @param request
     * @return
     */
    Integer studentRegister(StudentRegisterRequest request);

    /**
     * 老师注册
     * @param request
     * @return
     */
    Integer tutorRegister(TutorRegisterRequest request);
}
