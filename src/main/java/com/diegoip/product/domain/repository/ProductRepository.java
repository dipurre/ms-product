package com.diegoip.product.domain.repository;

import com.diegoip.product.domain.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveSortingRepository<Product, Long>, ReactiveCrudRepository<Product, Long> {
    Flux<Product> findByNameContaining(String name, Pageable pageable);
    Flux<Product> findAllBy(Pageable pageable);
}
