package com.example.app.controller;

import com.example.app.model.User;
import com.example.app.model.dto.UserDTO;
import com.example.app.repository.IUserRepository;
import com.example.app.service.IUserService;
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
    private IUserService userService;

    @GetMapping(path="/users")
    public @ResponseBody Iterable<UserDTO> getUsers(){
        return userService.getAllUsers();
    }

    @GetMapping(path="/users/{id}", produces = "application/json")
    public @ResponseBody ResponseEntity<UserDTO> getUser(@PathVariable("id") Integer id) {
        UserDTO userDTO = userService.getUserById(id);
        if(userDTO == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }
    }

    @PostMapping(path="/users", produces = "application/json")
    public void createUser(@RequestBody UserDTO userDTO ) {
        userService.createUser(userDTO);
    }

    @PatchMapping(path="/users/{id}", produces = "application/json")
    public @ResponseBody void updateUser(@PathVariable("id") Integer id, @RequestBody UserDTO userDTO ) {
        userService.updateUserWithId(id, userDTO);
    }

    @DeleteMapping(path="/users/{id}", produces = "application/json")
    public @ResponseBody void deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUserWithId(id);
    }
}
