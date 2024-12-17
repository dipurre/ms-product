package com.diegoip.product.api.service;

import com.diegoip.product.api.model.ProductRequest;
import com.diegoip.product.api.model.ProductResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {
    Mono<ProductResponse> addProduct(Mono<ProductRequest> productRequest);
    Mono<Void> deleteProduct(Long productId);
    Mono<ProductResponse> getProduct(Long productId);
    Flux<ProductResponse> listProducts(Integer limit, Integer offset);
    Flux<ProductResponse> searchProducts(Integer limit, Integer offset, String name);
    Mono<ProductResponse> updateProduct(Long productId, Mono<ProductRequest> productRequest);
}
