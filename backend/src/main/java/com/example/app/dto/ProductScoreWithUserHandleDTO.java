package com.example.app.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ProductScoreWithUserHandleDTO {
    @Getter
    @Setter
    private ProductScoreDTO productScoreDTO;
    @Getter
    @Setter
    private String userHandle;
}
