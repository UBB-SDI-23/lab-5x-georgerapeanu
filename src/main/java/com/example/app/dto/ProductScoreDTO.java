package com.example.app.dto;

import com.example.app.dto.model.ProductDTO;

public class ProductScoreDTO{
    private ProductDTO productDTO;
    private Double score;

    public ProductScoreDTO() {
    }

    public ProductScoreDTO(ProductDTO productDTO, Double score) {
        this.productDTO = productDTO;
        this.score = score;
    }

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
