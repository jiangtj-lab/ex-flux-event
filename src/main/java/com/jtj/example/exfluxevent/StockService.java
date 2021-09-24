package com.jtj.example.exfluxevent;

import com.jtj.example.exfluxevent.event.ObjectEventNotifier;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

@Slf4j
public class StockService {

    public Publisher<Void> initStockWithGoods1(ObjectEventNotifier<Goods> notifier) {
        ObjectEventNotifier.Type type = notifier.getType();
        if (type == ObjectEventNotifier.Type.Create) {
            log.error("Create stock for: " + notifier.getObject().getName());
            return Mono.just(Stock.of(1, 0)).then();
        }
        return Mono.empty();
    }

    public Publisher<Void> initStockWithGoods2(ObjectEventNotifier<Goods> notifier) {
        log.error("Another listener for Goods: " + notifier.getObject().getName());
        return Mono.empty();
    }

}
