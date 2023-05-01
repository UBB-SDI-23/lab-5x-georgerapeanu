package com.example.app.dto;

import com.example.app.dto.model.ProductDTO;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ProductScoreDTO{
    @Getter
    @Setter
    private ProductDTO productDTO;
    @Getter
    @Setter
    private Double score;

}
