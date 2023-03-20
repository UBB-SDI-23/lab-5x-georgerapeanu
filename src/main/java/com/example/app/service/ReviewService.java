package com.example.app.service;

import com.example.app.model.Review;
import com.example.app.model.User;
import com.example.app.model.dto.ReviewDTO;
import com.example.app.repository.IProductRepository;
import com.example.app.repository.IReviewRepository;
import com.example.app.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

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

}
