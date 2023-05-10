package com.example.app.service;

import com.example.app.exceptions.AppException;
import com.example.app.model.User;
import com.example.app.model.UserPreference;

public interface IUserService {
    public User getUserByHandle(String handle) throws AppException;
    public void setUserPreference(String handle, UserPreference userPreference) throws AppException;
}
