package com.example.app.controller;

import com.example.app.model.User;
import com.example.app.repository.IUserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.util.ArrayUtils;

import javax.xml.crypto.Data;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private IUserRepository userRepository;

    @GetMapping(path="/users")
    public @ResponseBody Iterable<User> getUsers(){
        return userRepository.findAll();
    }

    @GetMapping(path="/users/{id}", produces = "application/json")
    public @ResponseBody User getUser(@PathVariable("id") Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @PostMapping(path="/users", produces = "application/json")
    public void createUser(@RequestBody User user ) {
        userRepository.save(user);
    }

    @PatchMapping(path="/users/{id}", produces = "application/json")
    public @ResponseBody void updateUser(@PathVariable("id") Integer id, @RequestBody User user ) {
        User repoUser = userRepository.findById(id).get();
        user.setId(repoUser.getId());
        userRepository.save(user);
    }

    @DeleteMapping(path="/users/{id}", produces = "application/json")
    public @ResponseBody void deleteUser(@PathVariable("id") Integer id) {
        userRepository.deleteById(id);
    }
}
