package com.example.app.controller;

import com.example.app.dto.model.ReviewDTO;
import com.example.app.exceptions.AppException;
import com.example.app.service.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Validated
@RestController
@CrossOrigin(origins = "*")
public class ReviewController {
    @Autowired
    IReviewService reviewService;

    @GetMapping(path="/reviews", produces = "application/json")
    public @ResponseBody ResponseEntity<ReviewDTO> getReview(
            @RequestParam("user_handle") String userHandle,
            @RequestParam("product_id") Integer productId
    ) {
        try {
            return new ResponseEntity<>(reviewService.getReview(userHandle, productId), HttpStatus.OK);
        } catch (AppException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping(path="/reviews", produces = "application/json")
    public ResponseEntity<Map<String, String>> createReview(
            @RequestBody ReviewDTO reviewDTO
    ) {
        Map<String, String> response = new HashMap<>();
        try {
            reviewService.createReview(reviewDTO);
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
            @RequestBody ReviewDTO reviewDTO
    ) {
        Map<String, String> response = new HashMap<>();
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
    public void deleteReview(
            @RequestParam("user_handle") String userHandle,
            @RequestParam("product_id") Integer productId) {
        reviewService.deleteReview(userHandle, productId);
    }
}
