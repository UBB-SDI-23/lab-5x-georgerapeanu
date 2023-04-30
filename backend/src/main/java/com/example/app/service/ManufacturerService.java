package com.example.app.service;

import com.example.app.dto.ManufacturerProductCountDTO;
import com.example.app.exceptions.AppException;
import com.example.app.model.Manufacturer;
import com.example.app.dto.model.ManufacturerDTO;
import com.example.app.model.User;
import com.example.app.repository.ManufacturerRepository;
import com.example.app.repository.UserRepository;
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
    @Autowired
    UserRepository userRepository;
    public Page<ManufacturerDTO> getAllManufacturers(Integer pageNumber, Integer pageSize){
        return manufacturerRepository.findAll(PageRequest.of(pageNumber, pageSize))
                .map(ManufacturerDTO::fromManufacturer);
    }

    public ManufacturerDTO getManufacturerById(Integer id) throws AppException {
        Manufacturer manufacturer = manufacturerRepository.findById(id).orElse(null);
        if(manufacturer == null){
            throw new AppException("No manufacturer with such id exists");
        }
        return ManufacturerDTO.fromManufacturer(manufacturer);
    }

    public void createManufacturer(ManufacturerDTO manufacturerDTO) throws AppException {
        User user = userRepository.findById(manufacturerDTO.getUserHandle()).orElse(null);
        if(user == null) {
            throw new AppException("Invalid user handle specified");
        }
        manufacturerRepository.save(ManufacturerDTO.toManufacturer(manufacturerDTO, user));
    }

    public void updateManufacturerWithId(Integer id, ManufacturerDTO manufacturerDTO ) throws AppException {
        Manufacturer repoManufacturer = manufacturerRepository.findById(id).orElse(null);
        if(repoManufacturer == null) {
            throw new AppException("No such manufacturer found in repository");
        }
        User user = userRepository.findById(manufacturerDTO.getUserHandle()).orElse(null);
        if(user == null) {
            throw new AppException("Invalid user handle specified");
        }
        Manufacturer manufacturer = ManufacturerDTO.toManufacturer(manufacturerDTO, user);
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
