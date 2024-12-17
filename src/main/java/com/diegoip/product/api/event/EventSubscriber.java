package com.diegoip.product.api.event;

import com.diegoip.product.api.service.StatisticsService;
import com.diegoip.product.domain.event.ProductEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventSubscriber {

    private final StatisticsService statisticsService;

    @Autowired
    public EventSubscriber(EventPublisher eventPublisher, StatisticsService statisticsService) {
        eventPublisher.getEvents()
                .subscribe(this::handleEvent);
        this.statisticsService = statisticsService;
    }

    private void handleEvent(ProductEvent event) {
        statisticsService.calculateStatistics(event).subscribe();
    }
}
