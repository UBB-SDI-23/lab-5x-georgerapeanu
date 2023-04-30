package com.example.app.dto;

import com.example.app.dto.model.ManufacturerDTO;
import lombok.*;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ManufacturerProductCountDTO {
    @Getter
    @Setter
    ManufacturerDTO manufacturerDTO;
    @Getter
    @Setter
    Integer productCount;
}
