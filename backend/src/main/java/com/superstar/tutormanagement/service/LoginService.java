package com.superstar.tutormanagement.service;

import com.superstar.tutormanagement.controller.request.LoginRequest;
import com.superstar.tutormanagement.controller.response.LoginResponse;

public interface LoginService {
    LoginResponse studentLogin(LoginRequest request);
    LoginResponse tutorLogin(LoginRequest request);
    LoginResponse commonLogin(LoginRequest request);
    LoginResponse adminLogin(LoginRequest request);
}
