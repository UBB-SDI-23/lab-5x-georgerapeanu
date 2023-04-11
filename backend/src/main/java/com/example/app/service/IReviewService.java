package com.example.app.service;

import com.example.app.dto.ProductScoreDTO;
import com.example.app.dto.model.ReviewDTO;

import java.util.List;

public interface IReviewService {
    List<ReviewDTO> getReviewsForUser(Integer id, Integer pageNumber, Integer pageSize);
    List<ReviewDTO> getReviewsForProduct(Integer id, Integer pageNumber, Integer pageSize);
    void createReview(Integer userId, Integer productId, ReviewDTO reviewDTO);
    void updateReview(Integer userId, Integer productId, ReviewDTO reviewDTO);
    void deleteReview(Integer userId, Integer productId);
}