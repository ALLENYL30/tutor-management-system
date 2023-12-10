package com.superstar.tutormanagement.service.impl;

import com.superstar.tutormanagement.constant.Cipher;
import com.superstar.tutormanagement.controller.request.LoginRequest;
import com.superstar.tutormanagement.controller.response.LoginResponse;
import com.superstar.tutormanagement.enums.ResultCodeEnum;
import com.superstar.tutormanagement.exception.TutorManagementException;
import com.superstar.tutormanagement.repository.dao.LoginDao;
import com.superstar.tutormanagement.repository.entity.UserRoleDO;
import com.superstar.tutormanagement.service.LoginService;
import com.superstar.tutormanagement.utils.MD5;
import com.superstar.tutormanagement.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private LoginDao loginDao;

    @Override
    public LoginResponse studentLogin(LoginRequest request) {
        String password = MD5.encrypt(request.getPassword());
        String account = request.getAccountNumber();
        int count = loginDao.studentLogin(account, password);
        if (count != 1) {
            throw new TutorManagementException(ResultCodeEnum.LOGIN_FAILED);
        }
        return new LoginResponse(count, TokenUtils.getToken(request), "student");
    }
    @Override
    public LoginResponse tutorLogin(LoginRequest request) {
        String password = MD5.encrypt(request.getPassword());
        String account = request.getAccountNumber();
        int count = loginDao.tutorLogin(account, password);
        if (count != 1) {
            throw new TutorManagementException(ResultCodeEnum.LOGIN_FAILED);
        }
        return new LoginResponse(count, TokenUtils.getToken(request), "tutor");
    }

    @Override
    public LoginResponse commonLogin(LoginRequest request) {
        String password = MD5.encrypt(request.getPassword());
        String account = request.getAccountNumber();
        int countStudent = loginDao.studentLogin(account, password);
        int countTutor = loginDao.tutorLogin(account, password);
        if (countStudent + countTutor != 1) {
            throw new TutorManagementException(ResultCodeEnum.LOGIN_FAILED);
        }

        String role = countStudent == 1 ? "student" : "tutor";
        UserRoleDO userRoleDo = new UserRoleDO(role, account);
        return new LoginResponse(1, TokenUtils.getToken(userRoleDo), role);
    }

    @Override
    public LoginResponse adminLogin(LoginRequest request) {
        String password = request.getPassword();
        String account = request.getAccountNumber();
        if (!password.equals(Cipher.ADMIN_PASSWORD) || !account.equals(Cipher.ADMIN_ACCOUNT)) {
            throw new TutorManagementException(ResultCodeEnum.LOGIN_FAILED);
        }
        UserRoleDO userRoleDO = new UserRoleDO("admin", account);
        return new LoginResponse(1, TokenUtils.getToken(userRoleDO), "admin");

    }
}
