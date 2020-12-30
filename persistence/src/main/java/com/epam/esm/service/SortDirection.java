package com.epam.esm.service;

import java.util.Optional;

public enum SortDirection {

    ASC("ASC"),
    DESC("DESC");

    private final String direction;

    SortDirection(String direction) {
        this.direction = direction;
    }

    public static Optional<SortDirection> fromString(String direction) {
        SortDirection[] values = SortDirection.values();
        for (SortDirection value : values) {
            if (direction.equalsIgnoreCase(value.getDirection())) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }

    public String getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "SortingDirection{" +
                "direction='" + direction + '\'' +
                '}';
    }

}
