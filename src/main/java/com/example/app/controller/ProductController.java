package com.example.app.controller;

import com.example.app.dto.model.ManufacturerDTO;
import com.example.app.dto.model.ProductDTO;
import com.example.app.dto.ProductScoreDTO;
import com.example.app.dto.model.ReviewDTO;
import com.example.app.service.IProductService;
import com.example.app.service.IReviewService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@NoArgsConstructor
@AllArgsConstructor
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
    public void createProduct(@Valid @RequestBody ProductDTO productDTO ) {
        productService.createProduct(productDTO);
    }

    @PatchMapping(path="/products/{id}", produces = "application/json")
    public @ResponseBody void updateProduct(@PathVariable("id") Integer id, @Valid @RequestBody ProductDTO productDTO ) {
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

    @GetMapping(path="/products/{id}/reviews", produces = "application/json")
    public @ResponseBody Iterable<ReviewDTO> getReviews(@PathVariable("id") Integer id) {
        return reviewService.getReviewsForProduct(id);
    }
    @PostMapping(path="/products/{id}/reviews/{user_id}", produces = "application/json")
    public void createReview(@PathVariable("id") Integer id, @PathVariable("user_id") Integer user_id, @RequestBody ReviewDTO reviewDTO) {
        reviewService.createReview(user_id, id, reviewDTO);
    }
    @PatchMapping(path="/products/{id}/reviews/{user_id}", produces = "application/json")
    public void updateReview(@PathVariable("id") Integer id, @PathVariable("user_id") Integer user_id, @RequestBody ReviewDTO reviewDTO) {
        reviewService.updateReview(user_id, id, reviewDTO);
    }
    @DeleteMapping(path="/products/{id}/reviews/{user_id}", produces = "application/json")
    public void deleteReview(@PathVariable("id") Integer id, @PathVariable("user_id") Integer user_id) {
        reviewService.deleteReview(user_id, id);
    }
}
