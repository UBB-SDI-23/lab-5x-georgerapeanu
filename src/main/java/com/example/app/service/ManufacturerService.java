package com.example.app.service;

import com.example.app.model.Manufacturer;
import com.example.app.repository.IManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManufacturerService implements IManufacturerService{
    @Autowired
    IManufacturerRepository manufacturerRepository;
    public Iterable<Manufacturer> getAllManufacturers(){
        return manufacturerRepository.findAll();
    }

    public Manufacturer getManufacturerById(Integer id) {
        Manufacturer manufacturer = manufacturerRepository.findById(id).orElse(null);
        if(manufacturer == null){
            return null;
        }
        return manufacturer;
    }

    public void createManufacturer(Manufacturer manufacturer) {
        manufacturerRepository.save(manufacturer);
    }

    public void updateManufacturerWithId(Integer id, Manufacturer manufacturer ) {
        Manufacturer repoManufacturer = manufacturerRepository.findById(id).get();
        manufacturer.setId(repoManufacturer.getId());
        manufacturerRepository.save(manufacturer);
    }

    public void deleteManufacturerWithId(Integer id) {
        manufacturerRepository.deleteById(id);
    }

}
