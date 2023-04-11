package com.example.app.dto.model;

import com.example.app.model.Manufacturer;
import com.example.app.model.Product;
import jakarta.validation.constraints.NotBlank;

import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ManufacturerDTO {
    private Integer id;
    @NotBlank
    private String name;
    private String description;
    private Date registerDate;
    public ManufacturerDTO() {
    }

    public ManufacturerDTO(Integer id, String name, String description, Date registerDate, List<Integer> product_ids) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.registerDate = registerDate;
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

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ManufacturerDTO that = (ManufacturerDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(registerDate, that.registerDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, registerDate);
    }

    @Override
    public String toString() {
        return "ManufacturerDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", registerDate=" + registerDate +
                '}';
    }
}
