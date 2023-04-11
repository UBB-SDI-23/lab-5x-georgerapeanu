package com.example.app.repository;

import com.example.app.dto.ProductScoreDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductRepository {
    Page<ProductScoreDTO> getProductsSortedByAverageScore(Pageable pageable);
}
