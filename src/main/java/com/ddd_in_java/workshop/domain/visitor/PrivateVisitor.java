package com.ddd_in_java.workshop.domain.visitor;

public record PrivateVisitor(String id, String city) implements Visitor {
    public String type() { return "private"; }
}
