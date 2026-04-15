package com.ddd_in_java.workshop.application;

import com.ddd_in_java.workshop.domain.ExternalVisitors;

public class Context {
    public final ExternalVisitors externalVisitors;

    private Context(ExternalVisitors externalVisitors) {
        this.externalVisitors = externalVisitors;
    }

    public static Context initialize(ExternalVisitors externalVisitors) {
        return new Context(externalVisitors);
    }
}
