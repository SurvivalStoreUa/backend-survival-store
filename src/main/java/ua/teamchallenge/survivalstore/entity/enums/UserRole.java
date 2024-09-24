package ua.teamchallenge.survivalstore.entity.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    SUPERADMIN("SUPERADMIN"),
    ADMIN("ADMIN"),
    USER("USER");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }
}
