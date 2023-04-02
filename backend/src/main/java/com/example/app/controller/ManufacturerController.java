package com.example.app.controller;

import com.example.app.dto.ManufacturerProductCountDTO;
import com.example.app.dto.model.ManufacturerDTO;
import com.example.app.dto.model.ProductDTO;
import com.example.app.service.IManufacturerService;
import com.example.app.service.IProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ManufacturerController {
    @Autowired
    private IManufacturerService manufacturerService;
    @Autowired
    private IProductService productService;

    @GetMapping(path="/manufacturers")
    public @ResponseBody Iterable<ManufacturerDTO> getManufacturers(){
        return manufacturerService.getAllManufacturers();
    }

    @GetMapping(path="/manufacturers/sorted-by-products")
    public @ResponseBody Iterable<ManufacturerProductCountDTO> getManufacturersSortedByProducts(){
        return productService.getManufacturersSortedByProducts();
    }

    @GetMapping(path="/manufacturers/{id}", produces = "application/json")
    public @ResponseBody ResponseEntity<ManufacturerDTO> getManufacturer(@PathVariable("id") Integer id) {
        ManufacturerDTO manufacturerDTO = manufacturerService.getManufacturerById(id);
        if(manufacturerDTO == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(manufacturerDTO, HttpStatus.OK);
        }
    }

    @GetMapping(path="/manufacturers/{id}/products", produces = "application/json")
    public @ResponseBody ResponseEntity<List<ProductDTO>> getManufacturerProducts(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(manufacturerService.getProductsByManufacturerId(id), HttpStatus.OK);
    }

    @PostMapping(path="/manufacturers", produces = "application/json")
    public void createManufacturer(@Valid @RequestBody ManufacturerDTO manufacturerDTO ) {
        manufacturerService.createManufacturer(manufacturerDTO);
    }

    @PatchMapping(path="/manufacturers/{id}", produces = "application/json")
    public @ResponseBody void updateManufacturer(@PathVariable("id") Integer id, @Valid @RequestBody ManufacturerDTO manufacturerDTO ) {
        manufacturerService.updateManufacturerWithId(id, manufacturerDTO);
    }

    @DeleteMapping(path="/manufacturers/{id}", produces = "application/json")
    public @ResponseBody void deleteManufacturer(@PathVariable("id") Integer id) {
        manufacturerService.deleteManufacturerWithId(id);
    }

    @PostMapping(path="/manufacturers/{id}/products", produces = "application/json")
    public void createManufacturer(@PathVariable("id") Integer id, @Valid @RequestBody List<Integer> product_ids) {
        product_ids.forEach(product_id -> {
            ProductDTO productDTO = productService.getProductById(product_id);
            productDTO.setManufacturerId(id);
            productService.updateProductWithId(product_id, productDTO);
        });
    }
}
