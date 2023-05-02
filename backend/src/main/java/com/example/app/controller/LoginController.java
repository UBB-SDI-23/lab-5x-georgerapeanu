package com.example.app.controller;

import com.example.app.exceptions.AppException;
import com.example.app.model.User;
import com.example.app.service.ILoginService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Validated
public class LoginController {

    @Autowired
    ILoginService loginService;

    @PostMapping("/register")
    ResponseEntity<Map<String, String>> register(@RequestBody @Valid User user) {
        Map<String, String> response = new HashMap<>();
        try {
            String token = loginService.register(user);
            response.put("token", token);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (AppException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register/{code}")
    ResponseEntity<Map<String, String>> activate(
            @PathVariable("code") String token
    ) {
        Map<String, String> response = new HashMap<>();
        try {
            loginService.activateUser(token);
            response.put("message", "Activated");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (AppException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    ResponseEntity<Map<String, String>> login(@RequestBody @Valid User user) {
        Map<String, String> response = new HashMap<>();
        try {
            String token = loginService.login(user);
            response.put("token", token);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (AppException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
