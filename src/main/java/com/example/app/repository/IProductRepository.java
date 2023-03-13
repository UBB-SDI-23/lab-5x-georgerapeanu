package com.example.app.repository;

import com.example.app.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p from Product p WHERE p.weight > ?1")
    Iterable<Product> findProductsWithWeightBiggerThan(Integer weight);
}
