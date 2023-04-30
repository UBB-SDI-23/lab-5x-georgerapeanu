package com.example.app.service;

import com.example.app.dto.ManufacturerProductCountDTO;
import com.example.app.dto.model.ManufacturerDTO;
import com.example.app.dto.model.ProductDTO;
import com.example.app.exceptions.AppException;
import org.springframework.data.domain.Page;

import java.util.List;


public interface IManufacturerService {
    Page<ManufacturerDTO> getAllManufacturers(Integer pageNumber, Integer pageSize);
    ManufacturerDTO getManufacturerById(Integer id) throws AppException;
    void createManufacturer(ManufacturerDTO manufacturer) throws AppException;
    void updateManufacturerWithId(Integer id, ManufacturerDTO user ) throws AppException;
    void deleteManufacturerWithId(Integer id);
    public Page<ManufacturerProductCountDTO> getManufacturersSortedByProducts(Integer pageNumber, Integer pageSize);
    public Page<ManufacturerProductCountDTO> getManufacturerProductCountsPage(Integer pageNumber, Integer pageSize);
}
