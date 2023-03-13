package com.example.app.service;

import com.example.app.model.Review;
import com.example.app.model.dto.ReviewDTO;

public interface IReviewService {
    Iterable<ReviewDTO> getAllReviews();
    ReviewDTO getReviewById(Integer id);
    void createReview(ReviewDTO product);
    void updateReviewWithId(Integer id, ReviewDTO user );
    void deleteReviewWithId(Integer id);
}