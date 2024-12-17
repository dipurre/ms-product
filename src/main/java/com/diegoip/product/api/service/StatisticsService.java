package com.diegoip.product.api.service;

import com.diegoip.product.api.model.StatisticsCategoryResponse;
import com.diegoip.product.domain.event.ProductEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StatisticsService {
    Flux<StatisticsCategoryResponse> getstatisticsCategories();
    Mono<Void> calculateStatistics(ProductEvent productEvent);
}
