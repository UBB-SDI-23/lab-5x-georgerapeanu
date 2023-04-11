package com.example.app.service;

import com.example.app.dto.ManufacturerProductCountDTO;
import com.example.app.dto.model.ManufacturerDTO;
import com.example.app.dto.model.ProductDTO;

import java.util.List;


public interface IManufacturerService {
    List<ManufacturerDTO> getAllManufacturers(Integer pageNumber, Integer pageSize);
    ManufacturerDTO getManufacturerById(Integer id);
    void createManufacturer(ManufacturerDTO manufacturer);
    void updateManufacturerWithId(Integer id, ManufacturerDTO user );
    void deleteManufacturerWithId(Integer id);
    public List<ManufacturerProductCountDTO> getManufacturersSortedByProducts(Integer pageNumber, Integer pageSize);
}
