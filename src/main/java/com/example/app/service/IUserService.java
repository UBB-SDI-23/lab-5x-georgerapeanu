package com.example.app.service;

import com.example.app.model.User;

public interface IUserService {
    Iterable<User> getAllUsers();
    User getUserById(Integer id);
    void createUser(User user);
    void updateUserWithId(Integer id, User user );
    void deleteUserWithId(Integer id);
}
