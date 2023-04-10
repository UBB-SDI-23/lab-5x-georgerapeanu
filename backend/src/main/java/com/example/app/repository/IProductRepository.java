package com.example.app.repository;

import com.example.app.model.Manufacturer;
import com.example.app.model.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p from Product p WHERE p.weight > ?1")
    List<Product> findProductsWithWeightBiggerThan(Integer weight);
    List<Product> findAllByManufacturer(Manufacturer manufacturer, PageRequest pageable);
}
