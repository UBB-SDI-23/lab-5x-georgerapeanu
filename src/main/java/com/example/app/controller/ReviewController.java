package com.example.app.controller;

import com.example.app.dto.model.ReviewDTO;
import com.example.app.service.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReviewController {
    @Autowired
    private IReviewService reviewService;

}

