package com.example.app.controller;

import com.example.app.model.Manufacturer;
import com.example.app.service.IManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ManufacturerController {
    @Autowired
    private IManufacturerService manufacturerService;

    @GetMapping(path="/manufacturers")
    public @ResponseBody Iterable<Manufacturer> getManufacturers(){
        return manufacturerService.getAllManufacturers();
    }

    @GetMapping(path="/manufacturers/{id}", produces = "application/json")
    public @ResponseBody ResponseEntity<Manufacturer> getManufacturer(@PathVariable("id") Integer id) {
        Manufacturer manufacturer = manufacturerService.getManufacturerById(id);
        if(manufacturer == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(manufacturer, HttpStatus.OK);
        }
    }

    @PostMapping(path="/manufacturers", produces = "application/json")
    public void createManufacturer(@RequestBody Manufacturer manufacturer ) {
        manufacturerService.createManufacturer(manufacturer);
    }

    @PatchMapping(path="/manufacturers/{id}", produces = "application/json")
    public @ResponseBody void updateManufacturer(@PathVariable("id") Integer id, @RequestBody Manufacturer manufacturer ) {
        manufacturerService.updateManufacturerWithId(id, manufacturer);
    }

    @DeleteMapping(path="/manufacturers/{id}", produces = "application/json")
    public @ResponseBody void deleteManufacturer(@PathVariable("id") Integer id) {
        manufacturerService.deleteManufacturerWithId(id);
    }
}
