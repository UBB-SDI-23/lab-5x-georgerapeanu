package com.example.app.service;

import com.example.app.exceptions.AppException;
import com.example.app.model.User;

public interface IUserService {
    public User getUserByHandle(String handle) throws AppException;
}
