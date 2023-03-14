package com.example.app.service;

import com.example.app.model.Product;
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
                .map(product -> {
                    return new ProductDTO(
                      product.getId(),
                      product.getName(),
                      product.getDescription(),
                      product.getPublishDate(),
                      product.getPrice(),
                      product.getWeight(),
                      product.getManufacturer().getId()
                    );
                }).collect(Collectors.toList());
    }

    public ProductDTO getProductById(Integer id) {
        Product product = productRepository.findById(id).orElse(null);
        if(product == null){
            return null;
        }
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPublishDate(),
                product.getPrice(),
                product.getWeight(),
                product.getManufacturer().getId()
        );
    }

    public void createProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPublishDate(productDTO.getPublishDate());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setManufacturer(manufacturerRepository.findById(productDTO.getManufacturerId()).get());
        productRepository.save(product);
    }

    public void updateProductWithId(Integer id, ProductDTO productDTO ) {
        Product repoProduct = productRepository.findById(id).get();
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPublishDate(productDTO.getPublishDate());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setManufacturer(manufacturerRepository.findById(productDTO.getManufacturerId()).get());
        product.setId(repoProduct.getId());
        productRepository.save(product);
    }

    public void deleteProductWithId(Integer id) {
        productRepository.deleteById(id);
    }

    public Iterable<ProductDTO> getAllProductsWithWeightBiggerThan(Integer weight){
        return productRepository.findProductsWithWeightBiggerThan(weight).stream()
                .map(product -> {
                    return new ProductDTO(
                            product.getId(),
                            product.getName(),
                            product.getDescription(),
                            product.getPublishDate(),
                            product.getPrice(),
                            product.getWeight(),
                            product.getManufacturer().getId()
                    );
                }).collect(Collectors.toList());
    }

}

