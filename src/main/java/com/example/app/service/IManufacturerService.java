package com.example.app.service;

import com.example.app.model.Manufacturer;
import com.example.app.model.Manufacturer;

public interface IManufacturerService {
    Iterable<Manufacturer> getAllManufacturers();
    Manufacturer getManufacturerById(Integer id);
    void createManufacturer(Manufacturer manufacturer);
    void updateManufacturerWithId(Integer id, Manufacturer user );
    void deleteManufacturerWithId(Integer id);
}
