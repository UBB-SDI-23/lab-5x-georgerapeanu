package com.example.app.service;

import com.example.app.model.Product;
import com.example.app.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements  IProductService{
    @Autowired
    IProductRepository productRepository;

    public Iterable<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    public void createProduct(Product product) {
        productRepository.save(product);
    }

    public void updateProductWithId(Integer id, Product product ) {
        Product repoProduct = productRepository.findById(id).get();
        product.setId(repoProduct.getId());
        productRepository.save(product);
    }

    public void deleteProductWithId(Integer id) {
        productRepository.deleteById(id);
    }

    public Iterable<Product> getAllProductsWithWeightBiggerThan(Integer weight){
        return productRepository.findProductsWithWeightBiggerThan(weight);
    }

}

