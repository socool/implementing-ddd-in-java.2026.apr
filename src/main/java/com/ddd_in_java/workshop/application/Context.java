package com.ddd_in_java.workshop.application;

import com.ddd_in_java.workshop.domain.ExternalVisitors;

public class Context {
    public final ExternalVisitors externalVisitors;
    public final VisitHistory visitHistory;

    private Context(ExternalVisitors externalVisitors, VisitHistory visitHistory) {
        this.externalVisitors = externalVisitors;
        this.visitHistory = visitHistory;
    }

    public static Context initialize(ExternalVisitors externalVisitors) {
        return new Context(externalVisitors, new VisitHistory());
    }
}
