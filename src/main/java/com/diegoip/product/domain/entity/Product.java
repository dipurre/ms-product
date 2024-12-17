package com.diegoip.product.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("product")
public class Product {

    @Id
    private Long id;

    @Column("name")
    private String name;

    @Column("description")
    private String description;

    @Column("category")
    private String category;

    @Column("stock")
    private Integer stock;

    @Column("price")
    private Double price;
}

