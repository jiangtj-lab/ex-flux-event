package com.jtj.example.exfluxevent.event;

import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

public class ObjectEventNotifier<T> implements ResolvableTypeProvider {

    private final T object;
    private final Type type;

    private ObjectEventNotifier(T object, Type type) {
        this.object = object;
        this.type = type;
    }

    public static <T> ObjectEventNotifier<T> from(T object, Type type) {
        return new ObjectEventNotifier<>(object, type);
    }

    @Override
    public ResolvableType getResolvableType() {
        return ResolvableType.forClassWithGenerics(getClass(), ResolvableType.forInstance(object));
    }

    public T getObject() {
        return this.object;
    }

    public Type getType() {
        return this.type;
    }

    public enum Type {
        Create, Update, Delete
    }
}
