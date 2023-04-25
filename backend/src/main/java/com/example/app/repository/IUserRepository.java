package com.example.app.repository;

import com.example.app.dto.UserReviewCountDTO;
import com.example.app.dto.model.UserDTO;

import java.util.List;

public interface IUserRepository {
    List<UserReviewCountDTO> getUserReviewCountFromList(List<UserDTO> users);
}
