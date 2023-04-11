package com.example.app.service;

import com.example.app.dto.ManufacturerProductCountDTO;
import com.example.app.dto.ProductScoreDTO;
import com.example.app.dto.model.ManufacturerDTO;
import com.example.app.dto.model.ProductDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProductService {
    Page<ProductDTO> getAllProducts(Integer pageNumber, Integer pageSize);
    ProductDTO getProductById(Integer id);
    void createProduct(ProductDTO productDTO);
    void updateProductWithId(Integer id, ProductDTO productDTO);
    void deleteProductWithId(Integer id);
    public Page<ProductDTO> getAllProductsWithWeightBiggerThan(Integer weight, Integer pageNumber, Integer pageSize);
    public ManufacturerDTO getManufacturerByProductId(Integer id);

    Page<ProductDTO> getProductsByManufacturerId(Integer id, Integer pageNumber, Integer pageSize);

    Page<ProductScoreDTO> getProductsSortedByScore(Integer pageNumber, Integer pageSize);

}