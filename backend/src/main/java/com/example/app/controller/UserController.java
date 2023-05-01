package com.example.app.controller;

import com.example.app.dto.UserCreatedCountDTO;
import com.example.app.dto.UserReviewCountDTO;
import com.example.app.dto.model.ReviewDTO;
import com.example.app.dto.model.UserProfileDTO;
import com.example.app.exceptions.AppException;
import com.example.app.service.IReviewService;
import com.example.app.service.IUserProfileService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@Validated
public class UserController {
    @Autowired
    private IUserProfileService userProfileService;

    @Autowired
    private IReviewService reviewService;

    @GetMapping(path="/users")
    public @ResponseBody Page<UserProfileDTO> getUsers(
            @RequestParam
            Integer pageNumber,
            @RequestParam
            @Min(value=4, message = "pageSize should be at least 4")
            @Max(value=10, message = "pageSize should be at most 10")
            Integer pageSize
    ){
        return userProfileService.getAllUserProfiles(pageNumber, pageSize);
    }

    @GetMapping(path="/users/{id}", produces = "application/json")
    public @ResponseBody ResponseEntity<UserProfileDTO> getUser(@PathVariable("id") String handle) {
        UserProfileDTO userProfileDTO = userProfileService.getUserProfileById(handle);
        if(userProfileDTO == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(userProfileDTO, HttpStatus.OK);
        }
    }

    @PatchMapping(path="/users/{id}", produces = "application/json")
    public @ResponseBody void updateUser(@PathVariable("id") String handle, @Valid @RequestBody UserProfileDTO userProfileDTO) {
        userProfileService.updateUserProfileWithId(handle, userProfileDTO);
    }

    @DeleteMapping(path="/users/{id}", produces = "application/json")
    public @ResponseBody void deleteUser(@PathVariable("id") String handle) {
        userProfileService.deleteUserProfileWithId(handle);
    }

    @GetMapping(path="/users/{id}/reviews", produces = "application/json")
    public @ResponseBody ResponseEntity<Page<ReviewDTO>> getReviews(
            @PathVariable("id") String handle,
            @RequestParam
            Integer pageNumber,
            @RequestParam
            @Min(value=4, message = "pageSize should be at least 4")
            @Max(value=10, message = "pageSize should be at most 10")
            Integer pageSize
    ) {
        try {
            return new ResponseEntity<>(reviewService.getReviewsForUser(handle, pageNumber, pageSize), HttpStatus.OK);
        } catch (AppException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path="/user-review-counts")
    public @ResponseBody Page<UserReviewCountDTO> getUsersReviewCountPage(
            @RequestParam
            Integer pageNumber,
            @RequestParam
            @Min(value=4, message = "pageSize should be at least 4")
            @Max(value=10, message = "pageSize should be at most 10")
            Integer pageSize
    ){
        return userProfileService.getUserReviewCountPage(pageNumber, pageSize);
    }

    @GetMapping(path="/user-created-count/{handle}", produces = "application/json")
    public @ResponseBody UserCreatedCountDTO getUserCreatedCount(
            @PathVariable("handle")
            String handle
    ) {
        return userProfileService.getUserCreatedCount(handle);
    }
}
