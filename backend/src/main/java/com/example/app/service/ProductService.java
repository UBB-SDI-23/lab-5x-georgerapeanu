package com.example.app.service;

import com.example.app.dto.ProductScoreDTO;
import com.example.app.dto.ProductScoreWithUserHandleDTO;
import com.example.app.exceptions.AppException;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class ProductService implements  IProductService{
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ManufacturerRepository manufacturerRepository;

    public Page<ProductDTO> getAllProducts(Integer pageNumber, Integer pageSize){
        return productRepository
                .findAll(PageRequest.of(pageNumber, pageSize))
                .map(ProductDTO::fromProduct);
    }

    public ProductDTO getProductById(Integer id) {
        Product product = productRepository.findById(id).orElse(null);
        if(product == null){
            return null;
        }
        return ProductDTO.fromProduct(product);
    }

    public void createProduct(ProductDTO productDTO, String user_handle) throws AppException {
        Optional<Manufacturer> manufacturer = manufacturerRepository.findById(productDTO.getManufacturerId());
        if(manufacturer.isEmpty()) {
            throw new AppException("Manufacturer not found");
        }
        if(!Objects.equals(manufacturer.get().getUser().getHandle(), user_handle)) {
            throw new AppException("token does not match with user");
        }

        productRepository.save(ProductDTO.toProduct(productDTO, manufacturer.get()));
    }

    public void updateProductWithId(Integer id, ProductDTO productDTO ) throws AppException {
        Product repoProduct = productRepository.findById(id).orElse(null);
        if(repoProduct == null) {
            throw new AppException("No such product exists");
        }
        Manufacturer manufacturer = manufacturerRepository.findById(productDTO.getManufacturerId()).orElse(null);
        if(manufacturer == null) {
            throw new AppException("No such manufacturer exists");
        }
        Product product = ProductDTO.toProduct(productDTO, manufacturer);
        product.setId(repoProduct.getId());
        productRepository.save(product);
    }

    public void deleteProductWithId(Integer id) {
        productRepository.deleteById(id);
    }

    public Page<ProductDTO> getAllProductsWithWeightBiggerThan(Integer weight, Integer pageNumber, Integer pageSize){
        return productRepository
                .findAll(ProductSpecifications.weightBiggerThan(weight), PageRequest.of(pageNumber, pageSize))
                .map(ProductDTO::fromProduct);
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
    public Page<ProductDTO> getProductsByManufacturerId(Integer id, Integer pageNumber, Integer pageSize) throws AppException {
        Manufacturer manufacturer = manufacturerRepository.findById(id).orElse(null);
        if(manufacturer == null){
            throw new AppException("No such manufacturer exists");
        }
        return productRepository
                .findAllByManufacturer(manufacturer, PageRequest.of(pageNumber, pageSize))
                .map(ProductDTO::fromProduct);
    }

    @Override
    public Page<ProductScoreDTO> getProductsSortedByScore(Integer pageNumber, Integer pageSize) {
        return productRepository
                .getProductsSortedByAverageScore(PageRequest.of(pageNumber, pageSize));
    }

    @Override
    public Page<ProductScoreDTO> getProductScoresPage(Integer weight, Integer pageNumber, Integer pageSize) {
        Page<ProductDTO> productDTOPage = productRepository
                .findAll(ProductSpecifications.weightBiggerThan(weight), PageRequest.of(pageNumber, pageSize))
                .map(ProductDTO::fromProduct);
        return new PageImpl<>(
                productRepository.getProductScoresFromList(productDTOPage.getContent()),
                PageRequest.of(pageNumber, pageSize),
                productDTOPage.getTotalElements()
        );
    }

    @Override
    public Page<ProductScoreWithUserHandleDTO> getProductScoresPageWithUsers(Integer weight, Integer pageNumber, Integer pageSize) {
        return this.getProductScoresPage(weight, pageNumber, pageSize)
                .map(productScoreDTO -> {
                    return new ProductScoreWithUserHandleDTO(
                            productScoreDTO,
                            manufacturerRepository.findById(productScoreDTO.getProductDTO().getManufacturerId()).get().getUserHandle()
                    );
                });
    }
}

