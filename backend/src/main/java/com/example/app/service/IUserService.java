package com.example.app.service;

import com.example.app.dto.model.UserDTO;

import java.util.List;

public interface IUserService {
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Integer id);
    void createUser(UserDTO userDTO);
    void updateUserWithId(Integer id, UserDTO userDTO);
    void deleteUserWithId(Integer id);
}
