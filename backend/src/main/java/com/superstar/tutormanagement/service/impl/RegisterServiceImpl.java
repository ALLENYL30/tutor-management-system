package com.superstar.tutormanagement.service.impl;

import com.superstar.tutormanagement.controller.request.StudentRegisterRequest;
import com.superstar.tutormanagement.controller.request.TutorRegisterRequest;
import com.superstar.tutormanagement.enums.ResultCodeEnum;
import com.superstar.tutormanagement.exception.TutorManagementException;
import com.superstar.tutormanagement.repository.dao.RegisterDao;
import com.superstar.tutormanagement.repository.dao.UpdateDao;
import com.superstar.tutormanagement.service.RegisterService;
import com.superstar.tutormanagement.utils.MD5;
import com.superstar.tutormanagement.utils.ParameterCheck;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Base64;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Resource
    private RegisterDao registerDao;

    @Resource
    private UpdateDao updateDao;

    @Override
    public Integer studentRegister(StudentRegisterRequest request) {
        if (! checkStudentInfo(request)) {
            throw new TutorManagementException(ResultCodeEnum.PARAM_ERROR);
        }
        int count = registerDao.isStudentExist(request.getAccount());
        if (count != 0) {
            throw new TutorManagementException(ResultCodeEnum.ACCOUNT_ERROR);
        }
        String password = MD5.encrypt(request.getPassword());
        byte[] image = Base64.getDecoder().decode(request.getImage());
        int res = registerDao.studentRegister(request.getAccount(), password, request.getUserName(), request.getAddress(),
                request.getPhone(), request.getTimeZone(), image);
        return res;
    }

    @Override
    public Integer tutorRegister(TutorRegisterRequest request) {
        if (! checkTutorInfo(request)) {
            throw new TutorManagementException(ResultCodeEnum.PARAM_ERROR);
        }
        int count = registerDao.isTutorExist(request.getAccount());
        if (count != 0) {
            throw new TutorManagementException(ResultCodeEnum.ACCOUNT_ERROR);
        }
        String password = MD5.encrypt(request.getPassword());
        byte[] image = Base64.getDecoder().decode(request.getImage());
        int res = registerDao.tutorRegister(request.getAccount(), password, request.getUserName(),request.getAddress(),
                request.getPhone(), request.getTimeZone(), image, request.getBio(), request.getFilePath());
        int tutorId =  updateDao.getTutorId(request.getAccount());
        /*
        插入打分表
         */
        int scoreTable = registerDao.register2Score(tutorId);
        return res;
    }

    /**
     * 学生注册参数校验
     * @param request
     */
    private boolean checkStudentInfo(StudentRegisterRequest request) {
        return ParameterCheck.emailCheck(request.getAccount());
    }

    /**
     * 老师注册参数校验
     * @param request
     */
    private boolean checkTutorInfo(TutorRegisterRequest request) {
        return ParameterCheck.emailCheck(request.getAccount());
    }
}