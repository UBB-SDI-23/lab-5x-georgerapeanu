package com.example.app.service;

import com.example.app.model.Manufacturer;
import com.example.app.dto.model.ManufacturerDTO;
import com.example.app.dto.model.ProductDTO;
import com.example.app.repository.IManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManufacturerService implements IManufacturerService{
    @Autowired
    IManufacturerRepository manufacturerRepository;
    public List<ManufacturerDTO> getAllManufacturers(){
        return manufacturerRepository.findAll().stream()
                .map(ManufacturerDTO::fromManufacturer).collect(Collectors.toList());
    }

    public ManufacturerDTO getManufacturerById(Integer id) {
        Manufacturer manufacturer = manufacturerRepository.findById(id).orElse(null);
        if(manufacturer == null){
            return null;
        }
        return ManufacturerDTO.fromManufacturer(manufacturer);
    }

    public void createManufacturer(ManufacturerDTO manufacturerDTO) {
        manufacturerRepository.save(ManufacturerDTO.toManufacturer(manufacturerDTO));
    }

    public void updateManufacturerWithId(Integer id, ManufacturerDTO manufacturerDTO ) {
        Manufacturer repoManufacturer = manufacturerRepository.findById(id).get();
        Manufacturer manufacturer = ManufacturerDTO.toManufacturer(manufacturerDTO);
        manufacturer.setId(repoManufacturer.getId());
        manufacturerRepository.save(manufacturer);
    }

    public void deleteManufacturerWithId(Integer id) {
        manufacturerRepository.deleteById(id);
    }

    @Override
    public List<ProductDTO> getProductsByManufacturerId(Integer id) {
        Manufacturer manufacturer = manufacturerRepository.findById(id).orElse(null);
        if(manufacturer == null){
            return new ArrayList<>();
        }
        return manufacturer.getProducts().stream().map(ProductDTO::fromProduct).collect(Collectors.toList());
    }

}
