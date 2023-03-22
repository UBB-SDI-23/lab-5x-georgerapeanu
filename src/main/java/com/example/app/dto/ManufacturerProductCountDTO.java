package com.example.app.dto;

import com.example.app.dto.model.ManufacturerDTO;

public class ManufacturerProductCountDTO {
    ManufacturerDTO manufacturerDTO;
    Integer productCount;

    public ManufacturerProductCountDTO() {
    }

    public ManufacturerProductCountDTO(ManufacturerDTO manufacturerDTO, Integer productCount) {
        this.manufacturerDTO = manufacturerDTO;
        this.productCount = productCount;
    }

    public ManufacturerDTO getManufacturerDTO() {
        return manufacturerDTO;
    }

    public void setManufacturerDTO(ManufacturerDTO manufacturerDTO) {
        this.manufacturerDTO = manufacturerDTO;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }
}
