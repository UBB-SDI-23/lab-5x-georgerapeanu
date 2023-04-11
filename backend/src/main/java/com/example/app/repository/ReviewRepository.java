package com.example.app.repository;

import com.example.app.model.Ids.ReviewId;
import com.example.app.model.Manufacturer;
import com.example.app.model.Product;
import com.example.app.model.Review;
import com.example.app.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, ReviewId> {
    Page<Review> findAllByUser(User user, PageRequest pageable);
    Page<Review> findAllByProduct(Product product, PageRequest pageable);
}
