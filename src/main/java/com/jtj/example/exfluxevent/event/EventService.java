package com.jtj.example.exfluxevent.event;

import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Service
public class EventService {

    public Map<String, List<Object>> store = new ConcurrentHashMap<>();

    @SuppressWarnings({"unchecked", "rawtypes"})
    public EventService(List<EventListener> listeners) {
        listeners.forEach(listener -> {
            this.register(listener.target(), listener::consume);
        });
    }

    public <T> void register(Class<T> target, Function<ObjectEventNotifier<T>, Publisher<Void>> consumer) {
        String name = target.getName();
        List<Object> consumers = store.getOrDefault(name, null);
        if (consumers == null) {
            consumers = new ArrayList<>();
            store.put(name, consumers);
        }
        consumers.add(consumer);
    }

    @SuppressWarnings({"unchecked"})
    public <T> Flux<Void> publish(ObjectEventNotifier.Type type, T target) {
        String name = target.getClass().getName();
        List<Object> consumers = store.getOrDefault(name, null);
        if (consumers == null) {
            return Flux.empty();
        }
        return Flux
            .fromIterable(consumers)
            .flatMap(obj -> {
                Function<ObjectEventNotifier<T>, Publisher<Void>> consumer = (Function<ObjectEventNotifier<T>, Publisher<Void>>) obj;
                Publisher<Void> apply = consumer.apply(ObjectEventNotifier.from(target, type));
                return apply == null ? Mono.empty() : apply;
            });
    }

    public <T> Mono<T> publishCreate(T target) {
        return this.publish(ObjectEventNotifier.Type.Create, target)
            .then(Mono.just(target));
    }

    public <T> Mono<T> publishUpdate(T target) {
        return this.publish(ObjectEventNotifier.Type.Update, target)
            .then(Mono.just(target));
    }

    public <T> Mono<T> publishDelete(T target) {
        return this.publish(ObjectEventNotifier.Type.Delete, target)
            .then(Mono.just(target));
    }

}
