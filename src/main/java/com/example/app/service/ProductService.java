package com.example.app.service;

import com.example.app.dto.ManufacturerProductCountDTO;
import com.example.app.model.Product;
import com.example.app.dto.model.ManufacturerDTO;
import com.example.app.dto.model.ProductDTO;
import com.example.app.repository.IManufacturerRepository;
import com.example.app.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
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

    @Override
    public Iterable<ManufacturerProductCountDTO> getManufacturersSortedByProducts() {
        HashMap<Integer, Integer> total_count = new HashMap<>();

        productRepository.findAll().stream().forEach(product -> {
            total_count.put(product.getManufacturer().getId(), total_count.getOrDefault(product.getManufacturer().getId(), 0) + 1);
        });
        List<ManufacturerProductCountDTO> result = manufacturerRepository.findAll().stream().map(manufacturer -> {
            return new ManufacturerProductCountDTO(ManufacturerDTO.fromManufacturer(manufacturer), total_count.getOrDefault(manufacturer.getId(), 0));
        }).collect(Collectors.toList());
        result.sort((x, y) -> {
            if(!Objects.equals(x.getProductCount(), y.getProductCount())){
                return x.getProductCount() > y.getProductCount() ? -1:1;
            }
            return 0;
        });
        return result;
    }


}

