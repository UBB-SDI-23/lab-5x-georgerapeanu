package com.example.app.controller;

import com.example.app.model.User;
import com.example.app.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@CrossOrigin(origins = "*")
@Validated
@RestController
public class AdminController {
    @Autowired
    IAdminService adminService;
    @PostMapping("/admin/drop-all")
    public ResponseEntity<Map<String, String>> drop_all(
            @RequestAttribute("user") User user
    ) {
        Map<String, String> response = new HashMap<>();
        if(!Objects.equals(user.getUserRole().getName(), "admin")) {
            response.put("error", "Unauthorized for this endpoint");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        adminService.dropAll();
        response.put("message", "dropped");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
