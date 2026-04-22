package com.ddd_in_java.workshop.domain.visitor;

public record BusinessVisitor(String address, String city, String email) implements Visitor {
    public String id()   { return address + "|" + city; }
    public String type() { return "business"; }
}
