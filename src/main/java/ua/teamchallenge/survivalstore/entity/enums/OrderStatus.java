package ua.teamchallenge.survivalstore.entity.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    CREATED("CREATED"),
    IN_PROGRESS("IN_PROGRESS"),
    DELIVERED("DELIVERED"),
    CANCELLED("CANCELLED");
    private final String name;

    OrderStatus(String name) {
        this.name = name;
    }
}
