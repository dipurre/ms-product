package com.diegoip.product.api.service.impl;

import com.diegoip.product.api.event.EventPublisher;
import com.diegoip.product.api.model.ProductRequest;
import com.diegoip.product.api.model.ProductResponse;
import com.diegoip.product.domain.entity.Product;
import com.diegoip.product.domain.event.ProductEvent;
import com.diegoip.product.domain.repository.ProductRepository;
import com.diegoip.product.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private EventPublisher eventPublisher;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void addProductWhenIsValid() {

        ProductRequest productRequest = TestUtil.parseJsonToObject("productRequest.json", ProductRequest.class);
        Product product = TestUtil.parseJsonToObject("product.json", Product.class);
        ProductResponse productResponse = TestUtil.parseJsonToObject("productResponse.json", ProductResponse.class);

        when(productRepository.save(any(Product.class)))
                .thenReturn(Mono.just(product));
        when(eventPublisher.publishEvent(any(ProductEvent.class)))
                .thenReturn(Mono.empty());

        StepVerifier.create(productService.addProduct(Mono.just(productRequest)))
                .expectNext(productResponse)
                .verifyComplete();
    }
    @Test
    void deleteProductWhenIsValid() {
        Product product = TestUtil.parseJsonToObject("product.json", Product.class);

        when(productRepository.findById(anyLong())).thenReturn(Mono.just(product));
        when(productRepository.deleteById(anyLong())).thenReturn(Mono.empty());
        when(eventPublisher.publishEvent(any(ProductEvent.class)))
                .thenReturn(Mono.empty());

        StepVerifier.create(productService.deleteProduct(4L))
                .verifyComplete();
    }

    @Test
    void getProductWhenIsValid() {
        Product product = TestUtil.parseJsonToObject("product.json", Product.class);
        ProductResponse productResponse = TestUtil.parseJsonToObject("productResponse.json", ProductResponse.class);

        when(productRepository.findById(anyLong())).thenReturn(Mono.just(product));

        StepVerifier.create(productService.getProduct(4L))
                .expectNext(productResponse)
                .verifyComplete();
    }

    @Test
    void listProductsWhenIsPageable() {
        Product product = TestUtil.parseJsonToObject("product.json", Product.class);
        ProductResponse productResponse = TestUtil.parseJsonToObject("productResponse.json", ProductResponse.class);

        when(productRepository.findAllBy(any(Pageable.class))).thenReturn(Flux.just(product));

        StepVerifier.create(productService.listProducts(4, 0))
                .expectNext(productResponse)
                .verifyComplete();
    }

    @Test
    void searchProductsWhenIsPageable() {
        Product product = TestUtil.parseJsonToObject("product.json", Product.class);
        ProductResponse productResponse = TestUtil.parseJsonToObject("productResponse.json", ProductResponse.class);

        when(productRepository.findByNameContaining(anyString(), any(Pageable.class))).thenReturn(Flux.just(product));

        StepVerifier.create(productService.searchProducts(4, 0, "tv"))
                .expectNext(productResponse)
                .verifyComplete();
    }

    @Test
    void updateProductWhenIsValid() {

        ProductRequest productRequest = TestUtil.parseJsonToObject("productRequest.json", ProductRequest.class);
        Product product = TestUtil.parseJsonToObject("product.json", Product.class);
        ProductResponse productResponse = TestUtil.parseJsonToObject("productResponse.json", ProductResponse.class);

        when(productRepository.save(any(Product.class)))
                .thenReturn(Mono.just(product));

        StepVerifier.create(productService.updateProduct(4L, Mono.just(productRequest)))
                .expectNext(productResponse)
                .verifyComplete();
    }



}
