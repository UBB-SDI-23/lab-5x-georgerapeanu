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
        return reviewRepository.findAll().stream().map(review -> {
            Integer user_id = -1;
            User user = review.getUser();
            if(user != null){
                user_id = user.getId();
            }
            return new ReviewDTO(
                    review.getId(),
                    user_id,
                    review.getProduct().getId(),
                    review.getScore(),
                    review.getComment(),
                    review.getPostedDate()
            );
        }).collect(Collectors.toList());
    }

    public ReviewDTO getReviewById(Integer id) {
        Review review = reviewRepository.findById(id).orElse(null);
        if(review == null){
            return null;
        }
        Integer user_id = -1;
        User user = review.getUser();
        if(user != null){
            user_id = user.getId();
        }
        return new ReviewDTO(
                review.getId(),
                user_id,
                review.getProduct().getId(),
                review.getScore(),
                review.getComment(),
                review.getPostedDate()
        );
    }

    public void createReview(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setUser(userRepository.findById(reviewDTO.getUserId()).get());
        review.setProduct(productRepository.findById(reviewDTO.getProductId()).get());
        review.setScore(reviewDTO.getScore());
        review.setComment(reviewDTO.getComment());
        review.setPostedDate(reviewDTO.getPostedDate());
        reviewRepository.save(review);
    }

    public void updateReviewWithId(Integer id, ReviewDTO reviewDTO ) {
        Review repoReview = reviewRepository.findById(id).get();
        Review review = new Review();
        review.setUser(userRepository.findById(reviewDTO.getUserId()).get());
        review.setProduct(productRepository.findById(reviewDTO.getProductId()).get());
        review.setScore(reviewDTO.getScore());
        review.setComment(reviewDTO.getComment());
        review.setPostedDate(reviewDTO.getPostedDate());
        review.setId(repoReview.getId());
        reviewRepository.save(review);
    }

    public void deleteReviewWithId(Integer id) {
        reviewRepository.deleteById(id);
    }

}
