package com.apex.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    @NotBlank
    @Size(min = 3, message = "Product Name should be greater than 3 Character")
    private String productName;
    private String image;

    @NotBlank
    @Size(min = 3, message = "Description should be greater than 3 Character")
    private String description;

    @NotBlank
    private Integer quantity;

    private Double price;
    private Double discount;
    private Double specialPrice;

    @ManyToOne
    @JoinColumn(name ="category_id")
    private Category category;
}
