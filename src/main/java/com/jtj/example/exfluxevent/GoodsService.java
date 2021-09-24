package com.jtj.example.exfluxevent;

import com.jtj.example.exfluxevent.event.EventService;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

public class GoodsService {

    @Resource
    private EventService eventService;

    public Mono<Goods> createGoods() {
        Goods apple = Goods.of(1, "苹果");
        return Mono.just(apple)
            .flatMap(eventService::publishCreate);
    }

}
