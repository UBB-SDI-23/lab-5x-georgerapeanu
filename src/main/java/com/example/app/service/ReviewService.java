package com.example.app.service;

import com.example.app.model.Review;
import com.example.app.model.User;
import com.example.app.model.dto.ProductDTO;
import com.example.app.model.dto.ProductScoreDTO;
import com.example.app.model.dto.ReviewDTO;
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

    public Iterable<ReviewDTO> getAllReviews(){
        return reviewRepository.findAll().stream().map(ReviewDTO::fromReview).collect(Collectors.toList());
    }

    public ReviewDTO getReviewById(Integer id) {
        Review review = reviewRepository.findById(id).orElse(null);
        if(review == null){
            return null;
        }
        return ReviewDTO.fromReview(review);
    }

    public void createReview(ReviewDTO reviewDTO) {
        reviewRepository.save(ReviewDTO.toReview(reviewDTO, userRepository.findById(reviewDTO.getUserId()).get(), productRepository.findById(reviewDTO.getProductId()).get()));
    }

    public void updateReviewWithId(Integer id, ReviewDTO reviewDTO ) {
        Review repoReview = reviewRepository.findById(id).get();
        Review updatedReview = ReviewDTO.toReview(reviewDTO, repoReview.getUser(), repoReview.getProduct());
        reviewRepository.save(updatedReview);
    }

    public void deleteReviewWithId(Integer id) {
        reviewRepository.deleteById(id);
    }

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

}
