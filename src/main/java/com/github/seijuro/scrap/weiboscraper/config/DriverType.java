package com.github.seijuro.scrap.weiboscraper.config;

import lombok.NonNull;

public enum DriverType {
    CHROME("chrome"),
    PHANTOMJS("phantomjs"),
    SAFARI("safari"),
    FIREFOX("firefox");

    @NonNull
    private final String driverName;

    public String getDriverName() {
        return this.driverName;
    }

    DriverType(String $driver) {
        this.driverName = $driver;
    }
}
