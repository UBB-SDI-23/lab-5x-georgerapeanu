package com.example.app.service;

import com.example.app.dto.ManufacturerProductCountDTO;
import com.example.app.model.Manufacturer;
import com.example.app.dto.model.ManufacturerDTO;
import com.example.app.repository.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManufacturerService implements IManufacturerService{
    @Autowired
    ManufacturerRepository manufacturerRepository;
    public Page<ManufacturerDTO> getAllManufacturers(Integer pageNumber, Integer pageSize){
        return manufacturerRepository.findAll(PageRequest.of(pageNumber, pageSize))
                .map(ManufacturerDTO::fromManufacturer);
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
    public Page<ManufacturerProductCountDTO> getManufacturersSortedByProducts(Integer pageNumber, Integer pageSize) {
        return manufacturerRepository
                .getManufacturersSortedByProductCount(PageRequest.of(pageNumber, pageSize));
    }

    @Override
    public Page<ManufacturerProductCountDTO> getManufacturerProductCountsPage(Integer pageNumber, Integer pageSize) {
        Page<ManufacturerDTO> manufacturerDTOPage = manufacturerRepository
                .findAll(PageRequest.of(pageNumber, pageSize))
                .map(ManufacturerDTO::fromManufacturer);

        return new PageImpl<>(
                manufacturerRepository.getManufacturerProductCountsFromList(manufacturerDTOPage.getContent()),
                PageRequest.of(pageNumber, pageSize),
                manufacturerDTOPage.getTotalElements()
        );
    }

}
