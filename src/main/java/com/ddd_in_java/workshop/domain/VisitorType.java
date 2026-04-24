package com.ddd_in_java.workshop.domain;

import java.util.Locale;

public enum VisitorType {
    PRIVATE,
    BUSINESS;

    public static VisitorType fromExternal(String value) {
        if (value == null) {
            return PRIVATE;
        }

        String normalized = value.trim().toUpperCase(Locale.ROOT);
        return normalized.contains("BUSINESS") ? BUSINESS : PRIVATE;
    }
}
