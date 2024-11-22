package com.github.tarcalia.dmlabtestproject.config;

import io.smallrye.config.ConfigMapping;

/**
 * Configuration class for the application.
 * Value(s) set in application.yaml and can be easily inject.
 */
@ConfigMapping(prefix = "weather")
public interface WeatherServiceConfig {
    String apiKey();
    String location();
    Integer snow();
}