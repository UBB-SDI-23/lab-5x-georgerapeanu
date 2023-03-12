package com.example.app.service;

import com.example.app.model.User;
import com.example.app.repository.IUserRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserService implements  IUserService{
    @Autowired
    IUserRepository userRepository;

    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public void updateUserWithId(Integer id, User user ) {
        User repoUser = userRepository.findById(id).get();
        user.setId(repoUser.getId());
        userRepository.save(user);
    }

    public void deleteUserWithId(Integer id) {
        userRepository.deleteById(id);
    }

}
