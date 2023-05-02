package com.example.app.service;

import com.example.app.exceptions.AppException;
import com.example.app.model.User;
import org.springframework.web.bind.annotation.RequestBody;


public interface ILoginService {
    public String register(@RequestBody User user) throws AppException;
    public String login(@RequestBody User user) throws AppException;
    public void activateUser(@RequestBody String token) throws AppException;
}
