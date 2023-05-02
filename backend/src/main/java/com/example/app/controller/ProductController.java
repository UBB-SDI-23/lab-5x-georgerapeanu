package com.example.app.controller;

import com.example.app.dto.ProductScoreWithUserHandleDTO;
import com.example.app.dto.model.ManufacturerDTO;
import com.example.app.dto.model.ProductDTO;
import com.example.app.dto.ProductScoreDTO;
import com.example.app.dto.model.ReviewDTO;
import com.example.app.exceptions.AppException;
import com.example.app.service.IProductService;
import com.example.app.service.IReviewService;
import com.example.app.util.JWTUtils;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@Validated
public class ProductController {
    @Autowired
    private IProductService productService;
    @Autowired
    private IReviewService reviewService;

    @GetMapping(path="/products")
    public @ResponseBody Page<ProductDTO> getProducts(
            @RequestParam
            Integer pageNumber,
            @RequestParam
            @Min(value=4, message = "pageSize should be at least 4")
            @Max(value=10, message = "pageSize should be at most 10")
            Integer pageSize
    ){
        return productService.getAllProducts( pageNumber, pageSize);
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
    public ResponseEntity<Map<String, String>> createProduct(
            @Valid @RequestBody ProductDTO productDTO,
            @RequestHeader("Authorization")
            String authHeader
    ) {
        Map<String, String> response = new HashMap<>();
        String user_handle = "";
        try {
            user_handle = JWTUtils.getUserHandleFromAuthHeader(authHeader);
        } catch (AppException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        try {
            productService.createProduct(productDTO, user_handle);
            response.put("message", "Product created");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AppException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(path="/products/{id}", produces = "application/json")
    public @ResponseBody ResponseEntity<Map<String, String>> updateProduct(@PathVariable("id") Integer id, @Valid @RequestBody ProductDTO productDTO ) {
        Map<String, String> response = new HashMap<>();
        try {
            productService.updateProductWithId(id, productDTO);
            response.put("message", "Review updated");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AppException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path="/products/{id}", produces = "application/json")
    public @ResponseBody void deleteProduct(@PathVariable("id") Integer id) {
        productService.deleteProductWithId(id);
    }

    @GetMapping(path="/products/weight-filter", produces = "application/json")
    public @ResponseBody Page<ProductDTO> getAllProductsWithWeightBiggerThan(
            @RequestParam Integer weight,
            @RequestParam
            Integer pageNumber,
            @RequestParam
            @Min(value=4, message = "pageSize should be at least 4")
            @Max(value=10, message = "pageSize should be at most 10")
            Integer pageSize
    ) {
        return productService.getAllProductsWithWeightBiggerThan(weight, pageNumber, pageSize);
    }

    @GetMapping(path="/products/sorted-by-reviews", produces = "application/json")
    public @ResponseBody Page<ProductScoreDTO> getAllProductsSortedByReviews(
            @RequestParam
            Integer pageNumber,
            @RequestParam
            @Min(value=4, message = "pageSize should be at least 4")
            @Max(value=10, message = "pageSize should be at most 10")
            Integer pageSize
    ){
        return productService.getProductsSortedByScore(pageNumber, pageSize);
    }

    @GetMapping(path="/products/{id}/reviews", produces = "application/json")
    public @ResponseBody ResponseEntity<Page<ReviewDTO>> getReviews(
            @PathVariable("id") Integer id,
            @RequestParam
            Integer pageNumber,
            @RequestParam
            @Min(value=4, message = "pageSize should be at least 4")
            @Max(value=10, message = "pageSize should be at most 10")
            Integer pageSize
    ) {
        try {
            return new ResponseEntity<>(reviewService.getReviewsForProduct(id, pageNumber, pageSize), HttpStatus.OK);
        } catch (AppException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path="/product-scores")
    public @ResponseBody Page<ProductScoreDTO> getProductScoresPage(
            @RequestParam Integer weight,
            @RequestParam
            Integer pageNumber,
            @RequestParam
            @Min(value=4, message = "pageSize should be at least 4")
            @Max(value=10, message = "pageSize should be at most 10")
            Integer pageSize
    ){
        return productService.getProductScoresPage(weight, pageNumber, pageSize);
    }

    @GetMapping(path="/product-scores-with-users")
    public @ResponseBody Page<ProductScoreWithUserHandleDTO> getProductScoresWithUsersPage(
            @RequestParam Integer weight,
            @RequestParam
            Integer pageNumber,
            @RequestParam
            @Min(value=4, message = "pageSize should be at least 4")
            @Max(value=10, message = "pageSize should be at most 10")
            Integer pageSize
    ){
        return productService.getProductScoresPageWithUsers(weight, pageNumber, pageSize);
    }
}
