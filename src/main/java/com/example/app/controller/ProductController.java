package com.example.app.controller;

import com.example.app.model.Product;
import com.example.app.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {
    @Autowired
    private IProductService productService;

    @GetMapping(path="/products")
    public @ResponseBody Iterable<Product> getProducts(){
        return productService.getAllProducts();
    }

    @GetMapping(path="/products/{id}", produces = "application/json")
    public @ResponseBody ResponseEntity<Product> getProduct(@PathVariable("id") Integer id) {
        Product product = productService.getProductById(id);
        if(product == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
    }

    @PostMapping(path="/products", produces = "application/json")
    public void createProduct(@RequestBody Product product ) {
        productService.createProduct(product);
    }

    @PatchMapping(path="/products/{id}", produces = "application/json")
    public @ResponseBody void updateProduct(@PathVariable("id") Integer id, @RequestBody Product product ) {
        productService.updateProductWithId(id, product);
    }

    @DeleteMapping(path="/products/{id}", produces = "application/json")
    public @ResponseBody void deleteProduct(@PathVariable("id") Integer id) {
        productService.deleteProductWithId(id);
    }

    @GetMapping(path="/products/weight-filter", produces = "application/json")
    public @ResponseBody Iterable<Product> getAllProductsWithWeightBiggerThan(@RequestParam Integer weight) {
        return productService.getAllProductsWithWeightBiggerThan(weight);
    }
}
