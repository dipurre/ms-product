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
@Table("statistics_category")
public class StatisticsCategory {

    @Id
    private Long id;

    @Column("name")
    private String name;

    @Column("quantity_products")
    private Integer quantityProducts;
}
