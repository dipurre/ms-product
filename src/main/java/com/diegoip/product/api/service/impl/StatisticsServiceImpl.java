package com.diegoip.product.api.service.impl;

import com.diegoip.product.api.model.StatisticsCategoryResponse;
import com.diegoip.product.api.service.StatisticsService;
import com.diegoip.product.domain.entity.StatisticsCategory;
import com.diegoip.product.domain.event.ProductEvent;
import com.diegoip.product.domain.repository.StatisticsCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsCategoryRepository statisticsCategoryRepository;

    @Override
    public Flux<StatisticsCategoryResponse> getstatisticsCategories() {
        return this.statisticsCategoryRepository
                .findAll()
                .map(statisticsCategory ->
                        StatisticsCategoryResponse.builder()
                                .quantityProducts(statisticsCategory.getQuantityProducts())
                                .name(statisticsCategory.getName())
                                .build()
                );
    }

    @Override
    public Mono<Void> calculateStatistics(ProductEvent productEvent) {
        Mono<StatisticsCategory> statisticsCategoryMono = statisticsCategoryRepository
                .findByName(productEvent.getProduct().getCategory())
                .switchIfEmpty(Mono.fromCallable(() -> StatisticsCategory.builder()
                        .quantityProducts(0)
                        .name(productEvent.getProduct().getCategory())
                        .build()));
        return statisticsCategoryMono
                .flatMap(statisticsCategory -> {
                    if (productEvent.getEventType() == ProductEvent.EventType.CREATE) {
                        statisticsCategory.setQuantityProducts(statisticsCategory.getQuantityProducts() + 1);
                    } else {
                        statisticsCategory.setQuantityProducts(statisticsCategory.getQuantityProducts() - 1);
                    }
                    return statisticsCategoryRepository.save(statisticsCategory);
                }).then();
    }


}
