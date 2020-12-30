package com.epam.esm.service;

import java.util.Optional;

public enum CertificateSortCriteria {

    ID("id"),
    NAME("name"),
    PRICE("price");

    private final String name;

    CertificateSortCriteria(String name) {
        this.name = name;
    }

    public static Optional<CertificateSortCriteria> fromString(String input) {
        CertificateSortCriteria[] values = CertificateSortCriteria.values();
        for (CertificateSortCriteria value : values) {
            if (input.equalsIgnoreCase(value.getName()) || input.equalsIgnoreCase(value.name())) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "SortParameter{" +
                "direction='" + name + '\'' +
                '}';
    }
}
