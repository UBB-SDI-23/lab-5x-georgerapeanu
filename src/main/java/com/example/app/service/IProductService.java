package com.example.app.service;

import com.example.app.model.Manufacturer;
import com.example.app.model.dto.ManufacturerDTO;
import com.example.app.model.dto.ProductDTO;

public interface IProductService {
    Iterable<ProductDTO> getAllProducts();
    ProductDTO getProductById(Integer id);
    void createProduct(ProductDTO productDTO);
    void updateProductWithId(Integer id, ProductDTO productDTO);
    void deleteProductWithId(Integer id);
    public Iterable<ProductDTO> getAllProductsWithWeightBiggerThan(Integer weight);
    public ManufacturerDTO getManufacturerByProductId(Integer id);
}