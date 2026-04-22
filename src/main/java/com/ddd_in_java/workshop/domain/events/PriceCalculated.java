package com.ddd_in_java.workshop.domain.events;

import com.ddd_in_java.workshop.domain.Price;
import com.ddd_in_java.workshop.domain.visitor.Visitor;

public record PriceCalculated(Visitor visitor, Price price) implements DomainEvent {}
