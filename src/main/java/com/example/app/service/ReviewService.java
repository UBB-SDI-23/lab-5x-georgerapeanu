package com.example.app.service;

import com.example.app.model.Ids.ReviewId;
import com.example.app.model.Product;
import com.example.app.model.Review;
import com.example.app.dto.model.ProductDTO;
import com.example.app.dto.ProductScoreDTO;
import com.example.app.dto.model.ReviewDTO;
import com.example.app.model.User;
import com.example.app.repository.IProductRepository;
import com.example.app.repository.IReviewRepository;
import com.example.app.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.HashMap;

@Service
public class ReviewService implements  IReviewService{
    @Autowired
    IReviewRepository reviewRepository;
    @Autowired
    IUserRepository userRepository;
    @Autowired
    IProductRepository productRepository;

    @Override
    public Iterable<ProductScoreDTO> getProductsSortedByScore() {
        HashMap<Integer, Integer> total_score = new HashMap<>();
        HashMap<Integer, Integer> review_count = new HashMap<>();
        reviewRepository.findAll().stream().forEach(review -> {
            total_score.put(review.getProduct().getId(), total_score.getOrDefault(review.getProduct().getId(), 0) + review.getScore());
            review_count.put(review.getProduct().getId(), review_count.getOrDefault(review.getProduct().getId(), 0) + 1);
        });
        List<ProductScoreDTO> resultList = productRepository.findAll().stream().map(product -> {
            ProductScoreDTO result = new ProductScoreDTO();
            result.setProductDTO(ProductDTO.fromProduct(product));
            Double score = (double) 0;

            if(total_score.containsKey(product.getId())) {
                score = ((double)total_score.get(product.getId())) / ((double)review_count.get(product.getId()));
            }

            result.setScore(score);
            return result;
        }).collect(Collectors.toList());
        resultList.sort((x, y) -> {
            if(!Objects.equals(x.getScore(), y.getScore())) {
                return x.getScore() > y.getScore() ? -1:1;
            }
            return 0;
        });
        return resultList;
    }

    @Override
    public Iterable<ReviewDTO> getReviewsForUser(Integer id) {
        return reviewRepository.findAll().stream().filter(x -> {
            User user = x.getUser();
            if(user == null) {
                return false;
            }
            return Objects.equals(user.getId(), id);
        }).map(ReviewDTO::fromReview).collect(Collectors.toList());
    }

    @Override
    public Iterable<ReviewDTO> getReviewsForProduct(Integer id) {
        return reviewRepository.findAll().stream().filter(x -> {
            Product product = x.getProduct();
            if(product == null) {
                return false;
            }
            return Objects.equals(product.getId(), id);
        }).map(ReviewDTO::fromReview).collect(Collectors.toList());
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
