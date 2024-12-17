package com.diegoip.product.api.service.impl;

import com.diegoip.product.api.model.StatisticsCategoryResponse;
import com.diegoip.product.domain.entity.Product;
import com.diegoip.product.domain.entity.StatisticsCategory;
import com.diegoip.product.domain.event.ProductEvent;
import com.diegoip.product.domain.repository.StatisticsCategoryRepository;
import com.diegoip.product.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StatisticsServiceImplTest {

    @Mock
    private StatisticsCategoryRepository statisticsCategoryRepository;

    @InjectMocks
    private StatisticsServiceImpl statisticsService;

    @Test
    void getstatisticsCategoriesShouldAllCategory() {

        StatisticsCategory statisticsCategory = TestUtil.parseJsonToObject("statisticsCategory.json", StatisticsCategory.class);
        StatisticsCategoryResponse statisticsCategoryResponse = TestUtil.parseJsonToObject("statisticsCategoryResponse.json", StatisticsCategoryResponse.class);

        when(statisticsCategoryRepository.findAll()).thenReturn(Flux.just(statisticsCategory));

        StepVerifier.create(statisticsService.getstatisticsCategories())
                .expectNext(statisticsCategoryResponse)
                .verifyComplete();
    }

    @Test
    void calculateStatisticsWhenEventIsCreate() {

        Product product = TestUtil.parseJsonToObject("product.json", Product.class);
        ProductEvent productEvent = ProductEvent.builder()
                .product(product)
                .eventType(ProductEvent.EventType.CREATE)
                .build();
        StatisticsCategory statisticsCategory = TestUtil.parseJsonToObject("statisticsCategory.json", StatisticsCategory.class);

        when(statisticsCategoryRepository.findByName(anyString())).thenReturn(Mono.just(statisticsCategory));
        when(statisticsCategoryRepository.save(any(StatisticsCategory.class))).thenReturn(Mono.just(statisticsCategory));

        StepVerifier.create(statisticsService.calculateStatistics(productEvent))
                .verifyComplete();
    }

    @Test
    void calculateStatisticsWhenEventIsDelete() {

        Product product = TestUtil.parseJsonToObject("product.json", Product.class);
        ProductEvent productEvent = ProductEvent.builder()
                .product(product)
                .eventType(ProductEvent.EventType.DELETE)
                .build();
        StatisticsCategory statisticsCategory = TestUtil.parseJsonToObject("statisticsCategory.json", StatisticsCategory.class);

        when(statisticsCategoryRepository.findByName(anyString())).thenReturn(Mono.just(statisticsCategory));
        when(statisticsCategoryRepository.save(any(StatisticsCategory.class))).thenReturn(Mono.just(statisticsCategory));

        StepVerifier.create(statisticsService.calculateStatistics(productEvent))
                .verifyComplete();
    }

    @Test
    void calculateStatisticsWhenStatisticsIsEmpty() {

        Product product = TestUtil.parseJsonToObject("product.json", Product.class);
        ProductEvent productEvent = ProductEvent.builder()
                .product(product)
                .eventType(ProductEvent.EventType.DELETE)
                .build();
        StatisticsCategory statisticsCategory = TestUtil.parseJsonToObject("statisticsCategory.json", StatisticsCategory.class);

        when(statisticsCategoryRepository.findByName(anyString())).thenReturn(Mono.empty());
        when(statisticsCategoryRepository.save(any(StatisticsCategory.class))).thenReturn(Mono.just(statisticsCategory));

        StepVerifier.create(statisticsService.calculateStatistics(productEvent))
                .verifyComplete();
    }
}
