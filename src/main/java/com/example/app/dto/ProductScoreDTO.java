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

    @Override
    public boolean equals(Object other) {
        if(other == this){
            return true;
        }
        if(!(other instanceof ProductScoreDTO)){
            return false;
        }
        return this.productDTO.equals(((ProductScoreDTO) other).productDTO) && this.score.equals(((ProductScoreDTO) other).score);
    }

    @Override
    public String toString() {
        return "ProductScoreDTO{" +
                "productDTO=" + productDTO +
                ", score=" + score +
                '}';
    }
}
