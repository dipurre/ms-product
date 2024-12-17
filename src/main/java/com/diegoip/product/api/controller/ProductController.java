package com.diegoip.product.api.controller;

import com.diegoip.product.api.ProductoApiDelegate;
import com.diegoip.product.api.model.ProductRequest;
import com.diegoip.product.api.model.ProductResponse;
import com.diegoip.product.api.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class ProductController implements ProductoApiDelegate {

    private final ProductService productService;

    @Override
    public Mono<ResponseEntity<ProductResponse>> addProduct(Mono<ProductRequest> productRequest, ServerWebExchange exchange) {
        return productService.addProduct(productRequest)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteProduct(Long productId, ServerWebExchange exchange) {
        return productService.deleteProduct(productId)
                .thenReturn(ResponseEntity.noContent().build());
    }

    @Override
    public Mono<ResponseEntity<ProductResponse>> getProduct(Long productId, ServerWebExchange exchange) {
        return productService.getProduct(productId)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Flux<ProductResponse>>> listProducts(Integer limit, Integer offset, ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok(productService.listProducts(limit, offset)));
    }

    @Override
    public Mono<ResponseEntity<Flux<ProductResponse>>> searchProducts(Integer limit, Integer offset, String name, ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok(productService.searchProducts(limit, offset, name)));
    }

    @Override
    public Mono<ResponseEntity<ProductResponse>> updateProduct(Long productId, Mono<ProductRequest> productRequest, ServerWebExchange exchange) {
        return productService.updateProduct(productId, productRequest)
                .map(ResponseEntity::ok);
    }
}
