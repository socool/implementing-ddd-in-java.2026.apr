package com.ddd_in_java.workshop.application;

import com.ddd_in_java.workshop.domain.events.DomainEvent;

public interface MessageBus {
    void publish(DomainEvent event);
}
