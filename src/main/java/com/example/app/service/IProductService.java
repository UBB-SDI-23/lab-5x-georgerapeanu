package com.example.app.service;

import com.example.app.dto.model.ManufacturerDTO;
import com.example.app.dto.model.ProductDTO;

public interface IProductService {
    Iterable<ProductDTO> getAllProducts();
    ProductDTO getProductById(Integer id);
    void createProduct(ProductDTO productDTO);
    void updateProductWithId(Integer id, ProductDTO productDTO);
    void deleteProductWithId(Integer id);
    public Iterable<ProductDTO> getAllProductsWithWeightBiggerThan(Integer weight);
    public ManufacturerDTO getManufacturerByProductId(Integer id);
}