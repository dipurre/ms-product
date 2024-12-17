package com.diegoip.product.api.service.impl;

import com.diegoip.product.api.event.EventPublisher;
import com.diegoip.product.api.model.ProductRequest;
import com.diegoip.product.api.model.ProductResponse;
import com.diegoip.product.api.service.ProductService;
import com.diegoip.product.domain.entity.Product;
import com.diegoip.product.domain.event.ProductEvent;
import com.diegoip.product.domain.repository.OffsetBasedPageRequest;
import com.diegoip.product.domain.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final EventPublisher eventPublisher;

    @Override
    public Mono<ProductResponse> addProduct(Mono<ProductRequest> productRequest) {
        return productRequest
                .map(productRequestToProduct(null))
                .flatMap(productRepository::save)
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(product -> {
                    eventPublisher.publishEvent(ProductEvent.builder()
                            .eventType(ProductEvent.EventType.CREATE)
                            .product(product)
                            .build())
                            .subscribe();

                })
                .map(productToProductResponse());
    }

    @Override
    public Mono<Void> deleteProduct(Long productId) {
        return productRepository.findById(productId)
                .flatMap(product -> productRepository
                        .deleteById(productId)
                        .thenReturn(product))
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(product -> {
                    eventPublisher.publishEvent(ProductEvent.builder()
                                    .eventType(ProductEvent.EventType.DELETE)
                                    .product(product)
                                    .build())
                            .subscribe();

                }).then();
    }

    @Override
    public Mono<ProductResponse> getProduct(Long productId) {
        return productRepository.findById(productId)
                .map(productToProductResponse());
    }


    @Override
    public Flux<ProductResponse> listProducts(Integer limit, Integer offset) {
        Pageable pageable = new OffsetBasedPageRequest(
                limit,
                offset,
                Sort.by(Sort.Direction.ASC, "id"));
        return productRepository.findAllBy(pageable)
                .map(productToProductResponse());
    }

    @Override
    public Flux<ProductResponse> searchProducts(Integer limit, Integer offset, String name) {
        Pageable pageable = new OffsetBasedPageRequest(
                limit,
                offset,
                Sort.by(Sort.Direction.ASC, "id"));
        return productRepository.findByNameContaining(name, pageable)
                .map(productToProductResponse());
    }

    @Override
    public Mono<ProductResponse> updateProduct(Long productId, Mono<ProductRequest> productRequest) {
        return productRequest
                .map(productRequestToProduct(productId))
                .flatMap(productRepository::save)
                .map(productToProductResponse());
    }

    private static Function<ProductRequest, Product> productRequestToProduct(Long productId) {
        return productRequest -> Product.builder()
                .price(productRequest.getPrice())
                .stock(productRequest.getStock())
                .category(productRequest.getCategory())
                .description(productRequest.getDescription())
                .name(productRequest.getName())
                .id(productId)
                .build();
    }

    private static Function<Product, ProductResponse> productToProductResponse() {
        return product -> ProductResponse.builder()
                .price(product.getPrice())
                .stock(product.getStock())
                .category(product.getCategory())
                .description(product.getDescription())
                .name(product.getName())
                .id(product.getId())
                .build();
    }
}
