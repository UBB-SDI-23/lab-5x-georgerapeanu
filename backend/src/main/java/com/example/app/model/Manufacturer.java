package com.example.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Integer id;
    @NotBlank(message = "Manufacturer name is mandatory")
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private Date registerDate;
    @OneToMany(mappedBy="manufacturer", fetch=FetchType.LAZY)
    @Getter
    @Setter
    List<Product> products;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_handle", nullable = false)
    @Getter
    @Setter
    private User user;

}
