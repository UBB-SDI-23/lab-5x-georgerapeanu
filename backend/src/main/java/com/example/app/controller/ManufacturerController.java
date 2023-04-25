package com.example.app.controller;

import com.example.app.dto.ManufacturerProductCountDTO;
import com.example.app.dto.model.ManufacturerDTO;
import com.example.app.dto.model.ProductDTO;
import com.example.app.exceptions.AppException;
import com.example.app.service.IManufacturerService;
import com.example.app.service.IProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@CrossOrigin(origins = "*")
public class ManufacturerController {
    @Autowired
    private IManufacturerService manufacturerService;
    @Autowired
    private IProductService productService;

    @GetMapping(path="/manufacturers")
    public @ResponseBody Page<ManufacturerDTO> getManufacturers(
            @RequestParam
            Integer pageNumber,
            @RequestParam
            @Min(value=4, message = "pageSize should be at least 4")
            @Max(value=10, message = "pageSize should be at most 10")
            Integer pageSize
    ){
        return manufacturerService.getAllManufacturers(pageNumber, pageSize);
    }

    @GetMapping(path="/manufacturers/sorted-by-products")
    public @ResponseBody Page<ManufacturerProductCountDTO> getManufacturersSortedByProducts(
            @RequestParam
            Integer pageNumber,
            @RequestParam
            @Min(value=4, message = "pageSize should be at least 4")
            @Max(value=10, message = "pageSize should be at most 10")
            Integer pageSize
    ){
        return manufacturerService.getManufacturersSortedByProducts(pageNumber, pageSize);
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
    public @ResponseBody ResponseEntity<Page<ProductDTO>> getManufacturerProducts(
            @PathVariable("id") Integer id,
            @RequestParam
            Integer pageNumber,
            @RequestParam
            @Min(value=4, message = "pageSize should be at least 4")
            @Max(value=10, message = "pageSize should be at most 10")
            Integer pageSize
    ) {
        try {
            return new ResponseEntity<>(productService.getProductsByManufacturerId(id, pageNumber, pageSize), HttpStatus.OK);
        } catch (AppException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
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
    public void createManufacturer(
            @PathVariable("id") Integer id,
            @Valid @RequestBody Page<Integer> product_ids
    ) {
        product_ids.forEach(product_id -> {
            ProductDTO productDTO = productService.getProductById(product_id);
            productDTO.setManufacturerId(id);
            try {
                productService.updateProductWithId(product_id, productDTO);
            } catch (AppException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @GetMapping(path="/manufacturer-product-counts")
    public @ResponseBody Page<ManufacturerProductCountDTO> getManufacturerProductCountsPage(
            @RequestParam
            Integer pageNumber,
            @RequestParam
            @Min(value=4, message = "pageSize should be at least 4")
            @Max(value=10, message = "pageSize should be at most 10")
            Integer pageSize
    ){
        return manufacturerService.getManufacturerProductCountsPage(pageNumber, pageSize);
    }
}
