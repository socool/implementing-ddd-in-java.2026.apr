package com.ddd_in_java.workshop.domain;

public record BusinessVisitor(String personId, String city) implements Visitor {
    public String type() { return "business"; }
}
