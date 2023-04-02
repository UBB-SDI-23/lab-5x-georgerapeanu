package com.example.app.dto.model;

import com.example.app.model.Manufacturer;
import com.example.app.model.Product;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Integer id;
    @NotBlank
    private String name;
    private String description;
    private Date publishDate;
    private Double price;
    private Integer weight;
    private Integer manufacturerId;

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


    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", publishDate=" + publishDate +
                ", price=" + price +
                ", weight=" + weight +
                ", manufacturerId=" + manufacturerId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDTO that = (ProductDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(publishDate, that.publishDate) && Objects.equals(price, that.price) && Objects.equals(weight, that.weight) && Objects.equals(manufacturerId, that.manufacturerId);
    }
}

