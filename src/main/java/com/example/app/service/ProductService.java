package com.example.app.service;

import com.example.app.model.Manufacturer;
import com.example.app.model.Product;
import com.example.app.model.dto.ManufacturerDTO;
import com.example.app.model.dto.ProductDTO;
import com.example.app.repository.IManufacturerRepository;
import com.example.app.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ProductService implements  IProductService{
    @Autowired
    IProductRepository productRepository;
    @Autowired
    IManufacturerRepository manufacturerRepository;

    public Iterable<ProductDTO> getAllProducts(){
        return productRepository.findAll().stream()
                .map(ProductDTO::fromProduct).collect(Collectors.toList());
    }

    public ProductDTO getProductById(Integer id) {
        Product product = productRepository.findById(id).orElse(null);
        if(product == null){
            return null;
        }
        return ProductDTO.fromProduct(product);
    }

    public void createProduct(ProductDTO productDTO) {
        productRepository.save(ProductDTO.toProduct(productDTO, manufacturerRepository.findById(productDTO.getManufacturerId()).get()));
    }

    public void updateProductWithId(Integer id, ProductDTO productDTO ) {
        Product repoProduct = productRepository.findById(id).get();
        Product product = ProductDTO.toProduct(productDTO, manufacturerRepository.findById(productDTO.getManufacturerId()).get());
        product.setId(repoProduct.getId());
        productRepository.save(product);
    }

    public void deleteProductWithId(Integer id) {
        productRepository.deleteById(id);
    }

    public Iterable<ProductDTO> getAllProductsWithWeightBiggerThan(Integer weight){
        return productRepository.findProductsWithWeightBiggerThan(weight).stream()
                .map(ProductDTO::fromProduct).collect(Collectors.toList());
    }

    @Override
    public ManufacturerDTO getManufacturerByProductId(Integer id) {
        Product product = productRepository.findById(id).orElse(null);
        if(product == null){
            return null;
        }
        return ManufacturerDTO.fromManufacturer(product.getManufacturer());
    }

}

