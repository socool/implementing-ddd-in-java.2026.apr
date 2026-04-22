package com.ddd_in_java.workshop.domain;

public record BusinessVisitor(String address, String city) implements Visitor {
    public String id()   { return address + "|" + city; }
    public String type() { return "business"; }
}
