package com.diegoip.product.api.event;

import com.diegoip.product.domain.event.ProductEvent;
import lombok.Getter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

@Component
public class EventPublisher {

    private FluxSink<ProductEvent> eventSink;

    @Getter
    private final Flux<ProductEvent> events;

    public EventPublisher() {
        Flux<ProductEvent> publisher = Flux.create(emitter -> this.eventSink = emitter);
        this.events = publisher.share();

    }

    public Mono<Void> publishEvent(ProductEvent productEvent) {
        return Mono.fromRunnable(() -> this.eventSink.next(productEvent)).then();
    }

}
