package com.example.app.service;

import com.example.app.model.dto.ProductScoreDTO;
import com.example.app.model.dto.ReviewDTO;

public interface IReviewService {
    Iterable<ReviewDTO> getAllReviews();
    ReviewDTO getReviewById(Integer id);
    void createReview(ReviewDTO reviewDTO);
    void updateReviewWithId(Integer id, ReviewDTO reviewDTO );
    void deleteReviewWithId(Integer id);
    Iterable<ProductScoreDTO> getProductsSortedByScore();
}