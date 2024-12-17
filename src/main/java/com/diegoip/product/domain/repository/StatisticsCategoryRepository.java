package com.diegoip.product.domain.repository;

import com.diegoip.product.domain.entity.StatisticsCategory;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface StatisticsCategoryRepository extends ReactiveCrudRepository<StatisticsCategory, Long> {
    Mono<StatisticsCategory> findByName(String name);
}
