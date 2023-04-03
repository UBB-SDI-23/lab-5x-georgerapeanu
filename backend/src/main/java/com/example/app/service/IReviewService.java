package com.example.app.service;

import com.example.app.dto.ProductScoreDTO;
import com.example.app.dto.model.ReviewDTO;

import java.util.List;

public interface IReviewService {
    List<ProductScoreDTO> getProductsSortedByScore();

    List<ReviewDTO> getReviewsForUser(Integer id);
    List<ReviewDTO> getReviewsForProduct(Integer id);
    void createReview(Integer userId, Integer productId, ReviewDTO reviewDTO);
    void updateReview(Integer userId, Integer productId, ReviewDTO reviewDTO);
    void deleteReview(Integer userId, Integer productId);
}