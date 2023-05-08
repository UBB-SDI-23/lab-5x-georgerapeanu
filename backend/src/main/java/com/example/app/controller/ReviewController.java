package com.example.app.controller;

import com.example.app.dto.model.ReviewDTO;
import com.example.app.exceptions.AppException;
import com.example.app.model.User;
import com.example.app.service.IReviewService;
import com.example.app.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Validated
@RestController
@CrossOrigin(origins = "*")
public class ReviewController {
    @Autowired
    IReviewService reviewService;

    @GetMapping(path="/reviews", produces = "application/json")
    public @ResponseBody ResponseEntity<ReviewDTO> getReview(
            @RequestParam("user_handle") String userHandle,
            @RequestParam("product_id") Integer productId,
            @RequestAttribute("user") User user
    ) {
        if(!user.getUserRole().getRead_all() && (!user.getUserRole().getRead_own() || !Objects.equals(user.getHandle(), userHandle))) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        try {
            return new ResponseEntity<>(reviewService.getReview(userHandle, productId), HttpStatus.OK);
        } catch (AppException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping(path="/reviews", produces = "application/json")
    public ResponseEntity<Map<String, String>> createReview(
            @RequestBody ReviewDTO reviewDTO,
            @RequestAttribute("user") User user
    ) {
        Map<String, String> response = new HashMap<>();

        if(!user.getUserRole().getCreate()) {
            response.put("error", "Not authorized to create resource");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        try {
            reviewService.createReview(reviewDTO);
            reviewDTO.setUserHandle(user.getHandle());
            response.put("message", "Review created");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AppException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @PatchMapping(path="/reviews", produces = "application/json")
    public ResponseEntity<Map<String, String>> updateReview(
            @RequestParam("user_handle") String userHandle,
            @RequestParam("product_id") Integer productId,
            @RequestBody ReviewDTO reviewDTO,
            @RequestAttribute("user") User user
    ) {
        Map<String, String> response = new HashMap<>();
        if(!user.getUserRole().getUpdate_all() && (!user.getUserRole().getUpdate_own() || !Objects.equals(userHandle, user.getHandle()))) {
            response.put("error", "Unauthorized to update resource");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        try {
            reviewService.updateReview(userHandle, productId, reviewDTO);
            response.put("message", "Review updated");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AppException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping(path="/reviews", produces = "application/json")
    public ResponseEntity<Map<String ,String>> deleteReview(
            @RequestParam("user_handle") String userHandle,
            @RequestParam("product_id") Integer productId,
            @RequestAttribute("user") User user
    ) {
        Map<String, String> response = new HashMap<>();
        if(!user.getUserRole().getDelete_all() && (!user.getUserRole().getDelete_own() || !Objects.equals(userHandle, user.getHandle()))) {
            response.put("error", "Unauthorized to update resource");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        reviewService.deleteReview(userHandle, productId);
        response.put("message", "Review deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
