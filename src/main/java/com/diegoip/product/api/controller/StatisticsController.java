package com.diegoip.product.api.controller;

import com.diegoip.product.api.EstadisticasApiDelegate;
import com.diegoip.product.api.model.StatisticsCategoryResponse;
import com.diegoip.product.api.service.StatisticsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Component
public class StatisticsController implements EstadisticasApiDelegate {

    private final StatisticsService statisticsService;

    @Override
    public Mono<ResponseEntity<Flux<StatisticsCategoryResponse>>> getstatisticsCategories(ServerWebExchange exchange) {
        Flux<StatisticsCategoryResponse> statisticsCategoryResponseFlux = statisticsService.getstatisticsCategories();
        return Mono.just(ResponseEntity.ok(statisticsCategoryResponseFlux));
    }
}
