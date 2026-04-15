package com.ddd_in_java.workshop.application;

import com.ddd_in_java.workshop.domain.ExternalVisitors;
import com.ddd_in_java.workshop.domain.VisitHistory;

public class Context {
    public final ExternalVisitors externalVisitors;
    public final VisitHistory visitHistory = new VisitHistory();

    private Context(ExternalVisitors externalVisitors) {
        this.externalVisitors = externalVisitors;
    }

    public static Context initialize(ExternalVisitors externalVisitors) {
        return new Context(externalVisitors);
    }
}
