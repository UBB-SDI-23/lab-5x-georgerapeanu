package com.example.app.interceptors;

import com.example.app.exceptions.AppException;
import com.example.app.model.User;
import com.example.app.service.IUserService;
import com.example.app.util.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(Objects.equals(request.getMethod(), "OPTIONS")) {
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }
        String authToken = request.getHeader("Authorization");
        if(authToken == null) {
            User user = userService.getUserByHandle("visitor");
            request.setAttribute("user", user);
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }
        try {
            String userHandle = JWTUtils.getUserHandleFromAuthHeader(authToken);
            User user = userService.getUserByHandle(userHandle);
            request.setAttribute("user", user);
        } catch(AppException exception) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader("Access-Control-Allow-Origin", "*");
            return false;
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
