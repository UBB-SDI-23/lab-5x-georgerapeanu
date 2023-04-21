package com.example.app.controller;

import com.example.app.dto.model.ReviewDTO;
import com.example.app.dto.model.UserDTO;
import com.example.app.exceptions.AppException;
import com.example.app.service.IReviewService;
import com.example.app.service.IUserService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@Validated
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IReviewService reviewService;

    @GetMapping(path="/users")
    public @ResponseBody Page<UserDTO> getUsers(
            @RequestParam
            Integer pageNumber,
            @RequestParam
            @Min(value=4, message = "pageSize should be at least 4")
            @Max(value=10, message = "pageSize should be at most 10")
            Integer pageSize
    ){
        return userService.getAllUsers(pageNumber, pageSize);
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
    public void createUser(@Valid @RequestBody UserDTO userDTO ) {
        userService.createUser(userDTO);
    }

    @PatchMapping(path="/users/{id}", produces = "application/json")
    public @ResponseBody void updateUser(@PathVariable("id") Integer id, @Valid @RequestBody UserDTO userDTO ) {
        userService.updateUserWithId(id, userDTO);
    }

    @DeleteMapping(path="/users/{id}", produces = "application/json")
    public @ResponseBody void deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUserWithId(id);
    }

    @GetMapping(path="/users/{id}/reviews", produces = "application/json")
    public @ResponseBody ResponseEntity<Page<ReviewDTO>> getReviews(
            @PathVariable("id") Integer id,
            @RequestParam
            Integer pageNumber,
            @RequestParam
            @Min(value=4, message = "pageSize should be at least 4")
            @Max(value=10, message = "pageSize should be at most 10")
            Integer pageSize
    ) {
        try {
            return new ResponseEntity<>(reviewService.getReviewsForUser(id, pageNumber, pageSize), HttpStatus.OK);
        } catch (AppException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping(path="/users/{id}/reviews/{product_id}", produces = "application/json")
    public ResponseEntity<Map<String, String>> createReview(@PathVariable("id") Integer id, @PathVariable("product_id") Integer product_id, @RequestBody ReviewDTO reviewDTO) {
        Map<String, String> response = new HashMap<>();
        try {
            reviewService.createReview(id, product_id, reviewDTO);
            response.put("message", "Review created");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AppException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @PatchMapping(path="/users/{id}/reviews/{product_id}", produces = "application/json")
    public ResponseEntity<Map<String, String> > updateReview(@PathVariable("id") Integer id, @PathVariable("product_id") Integer product_id, @RequestBody ReviewDTO reviewDTO) {
        Map<String, String> response = new HashMap<>();
        try {
            reviewService.updateReview(id, product_id, reviewDTO);
            response.put("message", "Review created");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AppException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping(path="/users/{id}/reviews/{product_id}", produces = "application/json")
    public void deleteReview(@PathVariable("id") Integer id, @PathVariable("product_id") Integer product_id) {
        reviewService.deleteReview(id, product_id);
    }
}
