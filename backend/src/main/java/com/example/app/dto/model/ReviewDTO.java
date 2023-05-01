package com.example.app.dto.model;

import com.example.app.model.Product;
import com.example.app.model.Review;
import com.example.app.model.User;
import lombok.*;

import java.sql.Date;
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ReviewDTO {
    @Getter
    @Setter
    private String userHandle;
    @Getter
    @Setter
    private Integer productId;
    @Getter
    @Setter
    private Integer score;
    @Getter
    @Setter
    private String comment;
    @Getter
    @Setter
    private Date postedDate;

    public static ReviewDTO fromReview(Review review) {
        return new ReviewDTO(
                review.getUser().getHandle(),
                review.getProduct().getId(),
                review.getScore(),
                review.getComment(),
                review.getPostedDate()
        );
    }

    public static Review toReview(ReviewDTO reviewDTO, User user, Product product) {
        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setScore(reviewDTO.getScore());
        review.setComment(reviewDTO.getComment());
        review.setPostedDate(reviewDTO.getPostedDate());
        return review;
    }
}
