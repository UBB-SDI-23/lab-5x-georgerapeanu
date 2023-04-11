package com.example.app.service;

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
    public List<ReviewDTO> getReviewsForUser(Integer id, Integer pageNumber, Integer pageSize) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            return new ArrayList<>();
        }
        return reviewRepository
                .findAllByUser(user.get(), PageRequest.of(pageNumber, pageSize))
                .stream()
                .map(ReviewDTO::fromReview)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> getReviewsForProduct(Integer id, Integer pageNumber, Integer pageSize) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()){
            return new ArrayList<>();
        }
        return reviewRepository
                .findAllByProduct(product.get(), PageRequest.of(pageNumber, pageSize))
                .stream()
                .map(ReviewDTO::fromReview)
                .collect(Collectors.toList());
    }

    @Override
    public void createReview(Integer userId, Integer productId, ReviewDTO reviewDTO) {
        reviewRepository.save(ReviewDTO.toReview(reviewDTO, userRepository.findById(userId).get(), productRepository.findById(productId).get()));
    }

    @Override
    public void updateReview(Integer userId, Integer productId, ReviewDTO reviewDTO) {
        Review review = ReviewDTO.toReview(reviewDTO, userRepository.findById(userId).get(), productRepository.findById(productId).get());
        review.setId(new ReviewId(userId, productId));
        reviewRepository.save(review);
    }

    @Override
    public void deleteReview(Integer userId, Integer productId) {
        reviewRepository.deleteById(new ReviewId(userId, productId));
    }

}
