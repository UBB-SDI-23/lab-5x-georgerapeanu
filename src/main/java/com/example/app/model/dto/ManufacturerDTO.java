package com.example.app.model.dto;

import com.example.app.model.Manufacturer;
import com.example.app.model.Product;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ManufacturerDTO {
    private Integer id;
    private String name;
    private String description;

    public ManufacturerDTO() {
    }

    public ManufacturerDTO(Integer id, String name, String description, Date registerDate, List<Integer> product_ids) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.registerDate = registerDate;
        this.product_ids = product_ids;
    }

    private Date registerDate;
    List<Integer> product_ids;

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

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public List<Integer> getProduct_ids() {
        return product_ids;
    }

    public void setProduct_ids(List<Integer> product_ids) {
        this.product_ids = product_ids;
    }

    public static ManufacturerDTO fromManufacturer(Manufacturer manufacturer){
        return new ManufacturerDTO(
                manufacturer.getId(),
                manufacturer.getName(),
                manufacturer.getDescription(),
                manufacturer.getRegisterDate(),
                manufacturer.getProducts().stream().map(Product::getId).collect(Collectors.toList())
        );
    }

    public static Manufacturer toManufacturer(ManufacturerDTO manufacturerDTO){
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(manufacturer.getId());
        manufacturer.setDescription(manufacturerDTO.getDescription());
        manufacturer.setName(manufacturerDTO.getName());
        manufacturer.setRegisterDate(manufacturerDTO.getRegisterDate());
        return manufacturer;
    }
}
