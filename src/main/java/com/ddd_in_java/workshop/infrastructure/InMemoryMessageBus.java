package com.ddd_in_java.workshop.infrastructure;

import com.ddd_in_java.workshop.application.MessageBus;
import com.ddd_in_java.workshop.domain.DomainEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class InMemoryMessageBus implements MessageBus {

    private final List<Consumer<DomainEvent>> subscribers = new ArrayList<>();

    public void subscribe(Consumer<DomainEvent> subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void publish(DomainEvent event) {
        subscribers.forEach(s -> s.accept(event));
    }
}
