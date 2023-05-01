package com.example.app.repository;

import com.example.app.dto.UserReviewCountDTO;
import com.example.app.dto.model.UserDTO;
import com.example.app.dto.model.UserProfileDTO;

import java.util.List;

public interface IUserRepository {
    List<UserReviewCountDTO> getUserReviewCountFromList(List<UserProfileDTO> userProfiles);
}
