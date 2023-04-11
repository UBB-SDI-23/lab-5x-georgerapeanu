package com.example.app.service;

import com.example.app.dto.ProductScoreDTO;
import com.example.app.model.Manufacturer;
import com.example.app.model.Product;
import com.example.app.dto.model.ManufacturerDTO;
import com.example.app.dto.model.ProductDTO;
import com.example.app.repository.ManufacturerRepository;
import com.example.app.repository.ProductRepository;
import com.example.app.repository.specification.ProductSpecifications;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class ProductService implements  IProductService{
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ManufacturerRepository manufacturerRepository;

    public List<ProductDTO> getAllProducts(Integer pageNumber, Integer pageSize){
        return productRepository
                .findAll(PageRequest.of(pageNumber, pageSize))
                .stream()
                .map(ProductDTO::fromProduct)
                .collect(Collectors.toList());
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

    public List<ProductDTO> getAllProductsWithWeightBiggerThan(Integer weight, Integer pageNumber, Integer pageSize){
        return productRepository
                .findAll(ProductSpecifications.weightBiggerThan(weight), PageRequest.of(pageNumber, pageSize))
                .stream()
                .map(ProductDTO::fromProduct)
                .collect(Collectors.toList());
    }

    @Override
    public ManufacturerDTO getManufacturerByProductId(Integer id) {
        Product product = productRepository.findById(id).orElse(null);
        if(product == null){
            return null;
        }
        return ManufacturerDTO.fromManufacturer(product.getManufacturer());
    }

    @Override
    public List<ProductDTO> getProductsByManufacturerId(Integer id, Integer pageNumber, Integer pageSize) {
        Manufacturer manufacturer = manufacturerRepository.findById(id).orElse(null);
        if(manufacturer == null){
            return new ArrayList<>();
        }
        return productRepository
                .findAllByManufacturer(manufacturer, PageRequest.of(pageNumber, pageSize))
                .stream()
                .map(ProductDTO::fromProduct)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductScoreDTO> getProductsSortedByScore(Integer pageNumber, Integer pageSize) {
        return productRepository
                .getProductsSortedByAverageScore(PageRequest.of(pageNumber, pageSize))
                .toList();
    }
}

