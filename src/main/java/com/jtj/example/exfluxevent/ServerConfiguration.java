package com.jtj.example.exfluxevent;

import com.jtj.example.exfluxevent.event.EventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfiguration {

    @Bean
    public GoodsService goodsService() {
        return new GoodsService();
    }

    @Bean
    public StockService stockService() {
        return new StockService();
    }

    @Bean
    public EventListener<Goods> listener1(StockService stockService) {
        return EventListener.register(Goods.class, stockService::initStockWithGoods1);
    }

    @Bean
    public EventListener<Goods> listener2(StockService stockService) {
        return EventListener.register(Goods.class, stockService::initStockWithGoods2);
    }

}
