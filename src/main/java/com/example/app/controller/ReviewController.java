package com.example.app.controller;

import com.example.app.model.dto.ReviewDTO;
import com.example.app.service.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReviewController {
    @Autowired
    private IReviewService reviewService;

    @GetMapping(path="/reviews")
    public @ResponseBody Iterable<ReviewDTO> getReviews(){
        return reviewService.getAllReviews();
    }

    @GetMapping(path="/reviews/{id}", produces = "application/json")
    public @ResponseBody ResponseEntity<ReviewDTO> getReview(@PathVariable("id") Integer id) {
        ReviewDTO reviewDTO = reviewService.getReviewById(id);
        if(reviewDTO == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
        }
    }

    @PostMapping(path="/reviews", produces = "application/json")
    public void createReview(@RequestBody ReviewDTO reviewDTO ) {
        reviewService.createReview(reviewDTO);
    }

    @PatchMapping(path="/reviews/{id}", produces = "application/json")
    public @ResponseBody void updateReview(@PathVariable("id") Integer id, @RequestBody ReviewDTO reviewDTO ) {
        reviewService.updateReviewWithId(id, reviewDTO);
    }

    @DeleteMapping(path="/reviews/{id}", produces = "application/json")
    public @ResponseBody void deleteReview(@PathVariable("id") Integer id) {
        reviewService.deleteReviewWithId(id);
    }
}

