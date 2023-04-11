package com.example.app.repository;

import com.example.app.model.Ids.ReviewId;
import com.example.app.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, ReviewId> {
}
