package com.silva.stefano.jwtvalidador.model;

public class JWTTokenModel {
    public JWTTokenModel(String name, String role, String seed) {
        Name = name;
        Role = role;
        Seed = seed;
    }

    private String Name;
    private String Role;
    private String Seed;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getSeed() {
        return Seed;
    }

    public void setSeed(String seed) {
        Seed = seed;
    }
}
