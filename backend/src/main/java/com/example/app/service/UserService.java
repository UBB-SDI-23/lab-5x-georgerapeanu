package com.example.app.service;

import com.example.app.model.User;
import com.example.app.dto.model.UserDTO;
import com.example.app.repository.IUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements  IUserService{
    @Autowired
    IUserRepository userRepository;

    public List<UserDTO> getAllUsers(){
        return userRepository.findAll().stream().map(UserDTO::fromUser).collect(Collectors.toList());
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

}
