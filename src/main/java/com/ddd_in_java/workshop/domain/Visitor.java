package com.ddd_in_java.workshop.domain;

public sealed interface Visitor permits PrivateVisitor, BusinessVisitor {
    static Visitor FromExternalVisitor(ExternalVisitor externalVisitor) {
        return switch (externalVisitor.type()) {
            case "private"  -> new PrivateVisitor(externalVisitor.id(), externalVisitor.city());
            case "business" -> new BusinessVisitor(externalVisitor.address(), externalVisitor.city());
            default -> throw new IllegalArgumentException("Unknown visitor type: " + externalVisitor.type());
        };
    }
    String id();
    String city();
    String type();
}
