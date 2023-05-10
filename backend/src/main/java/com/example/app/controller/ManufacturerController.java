package com.example.app.controller;

import com.example.app.dto.ManufacturerProductCountDTO;
import com.example.app.dto.model.ManufacturerDTO;
import com.example.app.dto.model.ProductDTO;
import com.example.app.exceptions.AppException;
import com.example.app.model.User;
import com.example.app.service.IManufacturerService;
import com.example.app.service.IProductService;
import com.example.app.util.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@Validated
@CrossOrigin(origins = "*")
public class ManufacturerController {
    @Autowired
    private IManufacturerService manufacturerService;
    @Autowired
    private IProductService productService;

    @GetMapping(path="/manufacturers")
    public @ResponseBody ResponseEntity<Page<ManufacturerDTO>> getManufacturers(
            @RequestParam
            Integer pageNumber,
            @RequestParam
            @Min(value=4, message = "pageSize should be at least 4")
            @Max(value=10, message = "pageSize should be at most 10")
            Integer pageSize,
            @RequestAttribute("user")
            User user
    ){
        if(!user.getUserRole().getRead_all()) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(manufacturerService.getAllManufacturers(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping(path="/manufacturers/sorted-by-products")
    public @ResponseBody ResponseEntity<Page<ManufacturerProductCountDTO>> getManufacturersSortedByProducts(
            @RequestParam
            Integer pageNumber,
            @RequestParam
            @Min(value=4, message = "pageSize should be at least 4")
            @Max(value=10, message = "pageSize should be at most 10")
            Integer pageSize,
            @RequestAttribute("user")
            User user
    ){
        if(!user.getUserRole().getRead_all()) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(manufacturerService.getManufacturersSortedByProducts(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping(path="/manufacturers/{id}", produces = "application/json")
    public @ResponseBody ResponseEntity<ManufacturerDTO> getManufacturer(
            @PathVariable("id") Integer id,
            @RequestAttribute("user") User user
    ) {
        ManufacturerDTO manufacturerDTO = null;
        try {
            manufacturerDTO = manufacturerService.getManufacturerById(id);
            if(!user.getUserRole().getRead_all() && (!user.getUserRole().getRead_own() || !Objects.equals(manufacturerDTO.getUserHandle(), user.getHandle()))) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(manufacturerDTO, HttpStatus.OK);
        } catch (AppException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
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
            Integer pageSize,
            @RequestAttribute("user") User user
    ) {
        ManufacturerDTO manufacturerDTO = null;
        try {
            manufacturerDTO = manufacturerService.getManufacturerById(id);
            if(!user.getUserRole().getRead_all() && (!user.getUserRole().getRead_own() || !Objects.equals(manufacturerDTO.getUserHandle(), user.getHandle()))) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(productService.getProductsByManufacturerId(id, pageNumber, pageSize), HttpStatus.OK);
        } catch (AppException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path="/manufacturers", produces = "application/json")
    public ResponseEntity<Map<String, String> > createManufacturer(
            @Valid @RequestBody ManufacturerDTO manufacturerDTO,
            @RequestAttribute("user") User user
    ) {
        Map<String, String> response = new HashMap<>();

        if(!user.getUserRole().getCreate()) {
            response.put("error", "Unauthorized to create resource");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        try {
            manufacturerDTO.setUserHandle(user.getHandle());
            manufacturerService.createManufacturer(manufacturerDTO);
            response.put("message", "Manufacturer created");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (AppException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(path="/manufacturers/{id}", produces = "application/json")
    public @ResponseBody ResponseEntity<Map<String, String>> updateManufacturer(
            @PathVariable("id") Integer id,
            @Valid @RequestBody ManufacturerDTO manufacturerDTO,
            @RequestAttribute("user") User user
    ) {
        Map<String, String> response = new HashMap<>();
        try {
            ManufacturerDTO oldManufacturerDTO = manufacturerService.getManufacturerById(id);
            if(!user.getUserRole().getUpdate_all() && (!user.getUserRole().getUpdate_own() || !Objects.equals(oldManufacturerDTO.getUserHandle(), user.getHandle()))) {
                response.put("error", "Unauthorized to update resource");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
            if(!user.getUserRole().getUpdate_all() && !Objects.equals(manufacturerDTO.getUserHandle(), user.getHandle())) {
                response.put("error", "Unauthorized to transfer resource between users");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
            manufacturerService.updateManufacturerWithId(id, manufacturerDTO);
            response.put("message", "Manufacturer updated");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AppException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path="/manufacturers/{id}", produces = "application/json")
    public @ResponseBody ResponseEntity<Map<String, String>> deleteManufacturer(
            @PathVariable("id") Integer id,
            @RequestAttribute("user") User user
    ) {
        Map<String, String> response = new HashMap<>();
        try {
            ManufacturerDTO manufacturerDTO = manufacturerService.getManufacturerById(id);
            if (!user.getUserRole().getDelete_all() && (!user.getUserRole().getDelete_own() || !Objects.equals(manufacturerDTO.getUserHandle(), user.getHandle()))) {
                response.put("error", "Unauthorized to delete resource");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
            manufacturerService.deleteManufacturerWithId(id);
        } catch (AppException e) {
            throw new RuntimeException(e);
        }
        response.put("message", "Manufacturer deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @PostMapping(path="/manufacturers/{id}/products", produces = "application/json")
//    public void moveProductsToManufacturers(
//            @PathVariable("id") Integer id,
//            @Valid @RequestBody List<Integer> product_ids
//    ) {
//        product_ids.forEach(product_id -> {
//            ProductDTO productDTO = productService.getProductById(product_id);
//            productDTO.setManufacturerId(id);
//            try {
//                productService.updateProductWithId(product_id, productDTO);
//            } catch (AppException e) {
//                throw new RuntimeException(e);
//            }
//        });
//    }

    @GetMapping(path="/manufacturer-product-counts")
    public @ResponseBody ResponseEntity<Page<ManufacturerProductCountDTO>> getManufacturerProductCountsPage(
            @RequestParam
            Integer pageNumber,
            @RequestParam
            @Min(value=4, message = "pageSize should be at least 4")
            @Max(value=10, message = "pageSize should be at most 10")
            Integer pageSize,
            @RequestAttribute("user") User user
    ){
        if(!user.getUserRole().getRead_all()) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(manufacturerService.getManufacturerProductCountsPage(pageNumber, pageSize), HttpStatus.OK);
    }
}
