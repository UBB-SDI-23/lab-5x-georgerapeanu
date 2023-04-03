package com.example.app.service;

import com.example.app.dto.ManufacturerProductCountDTO;
import com.example.app.dto.model.ManufacturerDTO;
import com.example.app.dto.model.ProductDTO;

import java.util.List;

public interface IProductService {
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(Integer id);
    void createProduct(ProductDTO productDTO);
    void updateProductWithId(Integer id, ProductDTO productDTO);
    void deleteProductWithId(Integer id);
    public List<ProductDTO> getAllProductsWithWeightBiggerThan(Integer weight);
    public ManufacturerDTO getManufacturerByProductId(Integer id);

    public List<ManufacturerProductCountDTO> getManufacturersSortedByProducts();
}