package com.example.app.service;

import com.example.app.exceptions.AppException;
import com.example.app.model.Ids.ReviewId;
import com.example.app.model.Product;
import com.example.app.model.Review;
import com.example.app.dto.model.ProductDTO;
import com.example.app.dto.ProductScoreDTO;
import com.example.app.dto.model.ReviewDTO;
import com.example.app.model.User;
import com.example.app.repository.ProductRepository;
import com.example.app.repository.ReviewRepository;
import com.example.app.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class ReviewService implements  IReviewService{
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;

    @Override
    public Page<ReviewDTO> getReviewsForUser(Integer id, Integer pageNumber, Integer pageSize) throws AppException {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new AppException("No such user exists");
        }
        return reviewRepository
                .findAllByUser(user.get(), PageRequest.of(pageNumber, pageSize))
                .map(ReviewDTO::fromReview);
    }

    @Override
    public Page<ReviewDTO> getReviewsForProduct(Integer id, Integer pageNumber, Integer pageSize) throws AppException {
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()){
            throw new AppException("No such product exists");
        }
        return reviewRepository
                .findAllByProduct(product.get(), PageRequest.of(pageNumber, pageSize))
                .map(ReviewDTO::fromReview);
    }

    @Override
    public ReviewDTO getReview(Integer userId, Integer productId) throws AppException {
        Optional<Review> review = reviewRepository.findById(new ReviewId(userId, productId));
        if(review.isEmpty()) {
            throw  new AppException("No such review exists");
        }
        return ReviewDTO.fromReview(review.get());
    }

    @Override
    public void createReview(ReviewDTO reviewDTO) throws AppException {
        User user = userRepository.findById(reviewDTO.getUserId()).orElse(null);
        if(user == null) {
            throw new AppException("No such user exists");
        }
        Product product = productRepository.findById(reviewDTO.getProductId()).orElse(null);
        if(product == null) {
            throw new AppException("No such product exists");
        }
        reviewRepository.save(ReviewDTO.toReview(reviewDTO, user, product));
    }

    @Override
    public void updateReview(Integer userId, Integer productId, ReviewDTO reviewDTO) throws AppException {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) {
            throw new AppException("No such user exists");
        }
        Product product = productRepository.findById(productId).orElse(null);
        if(product == null) {
            throw new AppException("No such product exists");
        }
        Review review = ReviewDTO.toReview(reviewDTO, user, product);
        review.setId(new ReviewId(userId, productId));
        reviewRepository.save(review);
    }

    @Override
    public void deleteReview(Integer userId, Integer productId) {
        reviewRepository.deleteById(new ReviewId(userId, productId));
    }

}
