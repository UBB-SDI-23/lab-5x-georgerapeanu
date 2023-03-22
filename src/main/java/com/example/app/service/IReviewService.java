package com.example.app.service;

import com.example.app.dto.ProductScoreDTO;
import com.example.app.dto.model.ReviewDTO;

public interface IReviewService {
    Iterable<ProductScoreDTO> getProductsSortedByScore();

    Iterable<ReviewDTO> getReviewsForUser(Integer id);
    Iterable<ReviewDTO> getReviewsForProduct(Integer id);
    void createReview(Integer userId, Integer productId, ReviewDTO reviewDTO);
    void updateReview(Integer userId, Integer productId, ReviewDTO reviewDTO);
    void deleteReview(Integer userId, Integer productId);
}