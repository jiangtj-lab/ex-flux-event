package com.jtj.example.exfluxevent.event;

import org.reactivestreams.Publisher;

import java.util.function.Function;

public interface EventListener<T> {

    Class<T> target();

    Publisher<Void> consume(ObjectEventNotifier<T> consumer);

    static <T> EventListener<T> register(Class<T> target, Function<ObjectEventNotifier<T>, Publisher<Void>> fn) {
        return new EventListener<>() {
            @Override
            public Class<T> target() {
                return target;
            }

            @Override
            public Publisher<Void> consume(ObjectEventNotifier<T> consumer) {
                return fn.apply(consumer);
            }
        };
    }
}
