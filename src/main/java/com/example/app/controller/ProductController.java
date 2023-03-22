package com.example.app.controller;

import com.example.app.model.Manufacturer;
import com.example.app.model.Product;
import com.example.app.model.dto.ManufacturerDTO;
import com.example.app.model.dto.ProductDTO;
import com.example.app.model.dto.ProductScoreDTO;
import com.example.app.service.IProductService;
import com.example.app.service.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {
    @Autowired
    private IProductService productService;
    @Autowired
    private IReviewService reviewService;
    @GetMapping(path="/products")
    public @ResponseBody Iterable<ProductDTO> getProducts(){
        return productService.getAllProducts();
    }

    @GetMapping(path="/products/{id}", produces = "application/json")
    public @ResponseBody ResponseEntity<ProductDTO> getProduct(@PathVariable("id") Integer id) {
        ProductDTO productDTO = productService.getProductById(id);
        if(productDTO == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(productDTO, HttpStatus.OK);
        }
    }

    @GetMapping(path="/products/{id}/manufacturer", produces = "application/json")
    public @ResponseBody ResponseEntity<ManufacturerDTO> getProductManufacturer(@PathVariable("id") Integer id) {
        ManufacturerDTO manufacturerDTO = productService.getManufacturerByProductId(id);
        if(manufacturerDTO == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(manufacturerDTO, HttpStatus.OK);
        }
    }

    @PostMapping(path="/products", produces = "application/json")
    public void createProduct(@RequestBody ProductDTO productDTO ) {
        productService.createProduct(productDTO);
    }

    @PatchMapping(path="/products/{id}", produces = "application/json")
    public @ResponseBody void updateProduct(@PathVariable("id") Integer id, @RequestBody ProductDTO productDTO ) {
        productService.updateProductWithId(id, productDTO);
    }

    @DeleteMapping(path="/products/{id}", produces = "application/json")
    public @ResponseBody void deleteProduct(@PathVariable("id") Integer id) {
        productService.deleteProductWithId(id);
    }

    @GetMapping(path="/products/weight-filter", produces = "application/json")
    public @ResponseBody Iterable<ProductDTO> getAllProductsWithWeightBiggerThan(@RequestParam Integer weight) {
        return productService.getAllProductsWithWeightBiggerThan(weight);
    }

    @GetMapping(path="/products/sorted-by-reviews", produces = "application/json")
    public @ResponseBody Iterable<ProductScoreDTO> getAllProductsSortedByReviews(){
        return reviewService.getProductsSortedByScore();
    }

}
