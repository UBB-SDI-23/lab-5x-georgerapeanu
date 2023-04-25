package com.example.app.service;

import com.example.app.dto.UserReviewCountDTO;
import com.example.app.model.User;
import com.example.app.dto.model.UserDTO;
import com.example.app.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserService implements  IUserService{
    @Autowired
    UserRepository userRepository;

    public Page<UserDTO> getAllUsers(Integer pageNumber, Integer pageSize){
        return userRepository
                .findAll(PageRequest.of(pageNumber, pageSize))
                .map(UserDTO::fromUser);
    }

    public UserDTO getUserById(Integer id) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            return null;
        }
        return UserDTO.fromUser(user);
    }

    public void createUser(UserDTO userDTO) {
        userRepository.save(UserDTO.toUser(userDTO));
    }

    public void updateUserWithId(Integer id, UserDTO userDTO ) {
        User user = UserDTO.toUser(userDTO);
        user.setId(id);
        userRepository.save(user);
    }

    public void deleteUserWithId(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public Page<UserReviewCountDTO> getUserReviewCountPage(Integer pageNumber, Integer pageSize) {
        Page<UserDTO> userDTOPage = userRepository
                .findAll(PageRequest.of(pageNumber, pageSize))
                .map(UserDTO::fromUser);

        return new PageImpl<>(
                userRepository.getUserReviewCountFromList(userDTOPage.getContent()),
                PageRequest.of(pageNumber, pageSize),
                userDTOPage.getTotalElements()
        );
    }

}
