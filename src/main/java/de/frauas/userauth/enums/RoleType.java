package de.frauas.userauth.enums;

public enum RoleType {
    LESER("LESER"),
    AUTOR("AUTOR"),
    MODERATOR("MODERATOR"),
    ADMIN("ADMIN");

    private String role;

    RoleType(String role) {
        this.role = role;
    }

    public String toString() {
        return role;
    }
}
