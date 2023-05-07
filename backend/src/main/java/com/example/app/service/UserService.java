package com.example.app.service;

import com.example.app.exceptions.AppException;
import com.example.app.model.User;
import com.example.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService{
    @Autowired
    UserRepository userRepository;

    @Override
    public User getUserByHandle(String handle) throws AppException {
        Optional<User> userOptional = userRepository.findById(handle);
        if(userOptional.isEmpty()) {
            throw new AppException("User not found");
        }
        return userOptional.get();
    }
}
