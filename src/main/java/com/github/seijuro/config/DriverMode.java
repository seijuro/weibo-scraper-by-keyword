package com.github.seijuro.config;

public enum DriverMode {
    REMOTE("remote"),
    LOCAL("local");

    private final String modeName;

    public String getModeName() {
        return this.modeName;
    }

    DriverMode(String $name) {
        this.modeName = $name;
    }
}
