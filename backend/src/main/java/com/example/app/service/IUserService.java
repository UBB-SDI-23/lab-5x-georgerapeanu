package com.example.app.service;

import com.example.app.dto.UserReviewCountDTO;
import com.example.app.dto.model.UserDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IUserService {
    Page<UserDTO> getAllUsers(Integer pageNumber, Integer pageSize);
    UserDTO getUserById(Integer id);
    void createUser(UserDTO userDTO);
    void updateUserWithId(Integer id, UserDTO userDTO);
    void deleteUserWithId(Integer id);
    Page<UserReviewCountDTO> getUserReviewCount(Integer pageNumber, Integer pageSize);
}
