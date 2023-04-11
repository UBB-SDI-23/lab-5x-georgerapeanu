package com.example.app.service;

import com.example.app.dto.ManufacturerProductCountDTO;
import com.example.app.dto.model.ManufacturerDTO;
import com.example.app.dto.model.ProductDTO;

import java.util.List;

public interface IProductService {
    List<ProductDTO> getAllProducts(Integer pageNumber, Integer pageSize);
    ProductDTO getProductById(Integer id);
    void createProduct(ProductDTO productDTO);
    void updateProductWithId(Integer id, ProductDTO productDTO);
    void deleteProductWithId(Integer id);
    public List<ProductDTO> getAllProductsWithWeightBiggerThan(Integer weight, Integer pageNumber, Integer pageSize);
    public ManufacturerDTO getManufacturerByProductId(Integer id);

    List<ProductDTO> getProductsByManufacturerId(Integer id, Integer pageNumber, Integer pageSize);
}