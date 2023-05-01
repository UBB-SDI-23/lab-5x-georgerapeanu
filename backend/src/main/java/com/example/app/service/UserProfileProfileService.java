package com.example.app.service;

import com.example.app.dto.UserReviewCountDTO;
import com.example.app.model.UserProfile;
import com.example.app.dto.model.UserProfileDTO;
import com.example.app.repository.UserProfileRepository;
import com.example.app.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserProfileProfileService implements IUserProfileService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserProfileRepository userProfileRepository;

    @Override
    public Page<UserProfileDTO> getAllUserProfiles(Integer pageNumber, Integer pageSize){
        return userProfileRepository
                .findAll(PageRequest.of(pageNumber, pageSize))
                .map(UserProfileDTO::fromUserProfile);
    }

    @Override
    public UserProfileDTO getUserProfileById(String handle) {
        UserProfile userProfile = userProfileRepository.findById(handle).orElse(null);
        if(userProfile == null){
            return null;
        }
        return UserProfileDTO.fromUserProfile(userProfile);
    }

    @Override
    public void createUserProfile(UserProfileDTO userProfileDTO) {
        userProfileRepository.save(UserProfileDTO.toUserProfile(userProfileDTO));
    }

    @Override
    public void updateUserProfileWithId(String handle, UserProfileDTO userProfileDTO) {
        UserProfile userProfile = UserProfileDTO.toUserProfile(userProfileDTO);
        userProfile.setHandle(handle);
        userProfileRepository.save(userProfile);
    }

    @Override
    public void deleteUserProfileWithId(String handle) {
        userRepository.deleteById(handle);
    }

    @Override
    public Page<UserReviewCountDTO> getUserReviewCountPage(Integer pageNumber, Integer pageSize) {
        Page<UserProfileDTO> userDTOPage = userProfileRepository
                .findAll(PageRequest.of(pageNumber, pageSize))
                .map(UserProfileDTO::fromUserProfile);

        return new PageImpl<>(
                userRepository.getUserReviewCountFromList(userDTOPage.getContent()),
                PageRequest.of(pageNumber, pageSize),
                userDTOPage.getTotalElements()
        );
    }

}
