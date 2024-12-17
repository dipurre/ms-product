package com.diegoip.product.domain.event;

import com.diegoip.product.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductEvent {
    public enum EventType {
        CREATE, DELETE
    }
    private EventType eventType;
    private Product product;
}
