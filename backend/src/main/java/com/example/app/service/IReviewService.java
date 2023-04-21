package com.example.app.service;

import com.example.app.dto.ProductScoreDTO;
import com.example.app.dto.model.ReviewDTO;
import com.example.app.exceptions.AppException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IReviewService {
    Page<ReviewDTO> getReviewsForUser(Integer id, Integer pageNumber, Integer pageSize) throws AppException;
    Page<ReviewDTO> getReviewsForProduct(Integer id, Integer pageNumber, Integer pageSize) throws AppException;
    ReviewDTO getReview(Integer userId, Integer productId) throws AppException;
    void createReview(ReviewDTO reviewDTO) throws AppException;
    void updateReview(Integer userId, Integer productId, ReviewDTO reviewDTO) throws AppException;
    void deleteReview(Integer userId, Integer productId);
}