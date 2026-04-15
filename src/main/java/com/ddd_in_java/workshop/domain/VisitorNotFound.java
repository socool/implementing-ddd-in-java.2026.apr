package com.ddd_in_java.workshop.domain;

public class VisitorNotFound extends RuntimeException {
    public VisitorNotFound(String id) {
        super("Visitor not found: " + id);
    }
}
