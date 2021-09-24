package com.jtj.example.exfluxevent;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import javax.annotation.Resource;

@SpringBootTest
class GoodsServiceTest {

    @Resource
    GoodsService goodsService;

    @Test
    void createGoods() {
        goodsService.createGoods()
            .as(StepVerifier::create)
            .expectNextCount(1)
            .verifyComplete();
    }
}