package com.example.app.dto;

import com.example.app.dto.model.ManufacturerDTO;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ManufacturerProductCountDTO that = (ManufacturerProductCountDTO) o;
        return Objects.equals(manufacturerDTO, that.manufacturerDTO) && Objects.equals(productCount, that.productCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(manufacturerDTO, productCount);
    }

    @Override
    public String toString() {
        return "ManufacturerProductCountDTO{" +
                "manufacturerDTO=" + manufacturerDTO +
                ", productCount=" + productCount +
                '}';
    }
}
