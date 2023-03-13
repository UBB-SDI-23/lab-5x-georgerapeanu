package com.example.app.service;

import com.example.app.model.Product;

public interface IProductService {
    Iterable<Product> getAllProducts();
    Product getProductById(Integer id);
    void createProduct(Product product);
    void updateProductWithId(Integer id, Product user );
    void deleteProductWithId(Integer id);
    public Iterable<Product> getAllProductsWithWeightBiggerThan(Integer weight);
}