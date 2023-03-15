package com.example.app.model.dto;

import com.example.app.model.Manufacturer;
import com.example.app.model.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.sql.Date;

@Entity
public class ProductDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String description;
    private Date publishDate;
    private Double price;
    private Integer weight;
    private Integer manufacturerId;

    public ProductDTO(Integer id, String name, String description, Date publishDate, Double price, Integer weight, Integer manufacturer_id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.publishDate = publishDate;
        this.price = price;
        this.weight = weight;
        this.manufacturerId = manufacturer_id;
    }

    public ProductDTO() {
    }

    public Integer getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Integer manufacturer_id) {
        this.manufacturerId = manufacturer_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public static ProductDTO fromProduct(Product product) {
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

    public static Product toProduct(ProductDTO productDTO, Manufacturer manufacturer) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPublishDate(productDTO.getPublishDate());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setManufacturer(manufacturer);
        return product;
    }
}

