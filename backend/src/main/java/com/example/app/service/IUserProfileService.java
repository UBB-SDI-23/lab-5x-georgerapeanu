package com.example.app.service;

import com.example.app.dto.UserReviewCountDTO;
import com.example.app.dto.model.UserProfileDTO;
import org.springframework.data.domain.Page;

public interface IUserProfileService {
    Page<UserProfileDTO> getAllUserProfiles(Integer pageNumber, Integer pageSize);
    UserProfileDTO getUserProfileById(String handle);
    void createUserProfile(UserProfileDTO userProfileDTO);
    void updateUserProfileWithId(String handle, UserProfileDTO userProfileDTO);
    void deleteUserProfileWithId(String handle);
    Page<UserReviewCountDTO> getUserReviewCountPage(Integer pageNumber, Integer pageSize);
}
