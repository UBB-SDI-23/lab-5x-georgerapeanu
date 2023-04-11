package com.example.app.service;

import com.example.app.dto.ManufacturerProductCountDTO;
import com.example.app.dto.model.ManufacturerDTO;
import com.example.app.dto.model.ProductDTO;
import org.springframework.data.domain.Page;

import java.util.List;


public interface IManufacturerService {
    Page<ManufacturerDTO> getAllManufacturers(Integer pageNumber, Integer pageSize);
    ManufacturerDTO getManufacturerById(Integer id);
    void createManufacturer(ManufacturerDTO manufacturer);
    void updateManufacturerWithId(Integer id, ManufacturerDTO user );
    void deleteManufacturerWithId(Integer id);
    public Page<ManufacturerProductCountDTO> getManufacturersSortedByProducts(Integer pageNumber, Integer pageSize);
}
