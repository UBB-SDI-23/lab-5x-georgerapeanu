package com.example.app.repository;

import com.example.app.dto.ProductScoreDTO;
import com.example.app.dto.model.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductRepository {
    Page<ProductScoreDTO> getProductsSortedByAverageScore(Pageable pageable);
    List<ProductScoreDTO> getProductScoresFromList(List<ProductDTO> productDTOS);
}
