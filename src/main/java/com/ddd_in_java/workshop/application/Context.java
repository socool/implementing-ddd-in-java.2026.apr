package com.ddd_in_java.workshop.application;

import com.ddd_in_java.workshop.domain.ExternalVisitors;
import com.ddd_in_java.workshop.domain.VisitHistories;
import com.ddd_in_java.workshop.infrastructure.InMemoryVisitHistories;

public class Context {
    public final ExternalVisitors externalVisitors;
    public final VisitHistories visitHistories = new InMemoryVisitHistories();

    private Context(ExternalVisitors externalVisitors) {
        this.externalVisitors = externalVisitors;
    }

    public static Context initialize(ExternalVisitors externalVisitors) {
        return new Context(externalVisitors);
    }
}
