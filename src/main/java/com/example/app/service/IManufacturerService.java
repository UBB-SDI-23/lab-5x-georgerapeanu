package com.example.app.service;

import com.example.app.model.dto.ManufacturerDTO;
import com.example.app.model.dto.ProductDTO;

import java.util.List;


public interface IManufacturerService {
    Iterable<ManufacturerDTO> getAllManufacturers();
    ManufacturerDTO getManufacturerById(Integer id);
    void createManufacturer(ManufacturerDTO manufacturer);
    void updateManufacturerWithId(Integer id, ManufacturerDTO user );
    void deleteManufacturerWithId(Integer id);
    List<ProductDTO> getProductsByManufacturerId(Integer id);
}
