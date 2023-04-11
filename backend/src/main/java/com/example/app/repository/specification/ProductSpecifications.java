package com.example.app.repository.specification;

import com.example.app.model.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {
    public static Specification<Product> weightBiggerThan(Integer weight) {
        return (product, cq, cb) -> cb.greaterThan(product.get("weight"), weight);
    }
}
