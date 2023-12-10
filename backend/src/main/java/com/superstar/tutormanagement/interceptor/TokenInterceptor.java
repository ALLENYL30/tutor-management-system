package com.superstar.tutormanagement.interceptor;

import com.superstar.tutormanagement.enums.ResultCodeEnum;
import com.superstar.tutormanagement.exception.TutorManagementException;
import com.superstar.tutormanagement.handler.TokenLimit;
import com.superstar.tutormanagement.utils.TokenStorage;
import com.superstar.tutormanagement.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * token校验拦截器
 */
@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private TokenStorage tokenStorage;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 判断请求是否方法的请求
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 获取方法种的注解
            TokenLimit tokenLimit = handlerMethod.getMethodAnnotation(TokenLimit.class);
            if (tokenLimit == null) {
                // 不需要token校验
                return true;
            }
            // token验证
            String token = request.getHeader("token");
            if (null == token || Objects.equals(token, "")) {
                throw new TutorManagementException(ResultCodeEnum.TOKEN_EMPTY);
            }
            if (!TokenUtils.isValid(token)) {
                throw new TutorManagementException(ResultCodeEnum.TOKEN_ERROR);
            }

            // token存储
            tokenStorage.setToken(token);
        }
        return true;
    }
}
