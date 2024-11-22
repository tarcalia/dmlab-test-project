package com.github.tarcalia.dmlabtestproject.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tarcalia.dmlabtestproject.client.WeatherRestClient;
import com.github.tarcalia.dmlabtestproject.config.WeatherServiceConfig;
import com.github.tarcalia.dmlabtestproject.entity.SunTimeStats;
import com.github.tarcalia.dmlabtestproject.entity.WeatherResponse;
import com.github.tarcalia.dmlabtestproject.repository.RedisService;
import com.github.tarcalia.dmlabtestproject.repository.WeatherDayRepository;
import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.Duration;
import java.util.Optional;

@ApplicationScoped
@Slf4j
public class WeatherService {
    private static final String ELEMENTS = "tempmax,tempmin,temp,datetime,feelslike,snow,windspeed,sunrise,sunset";
    private static final String INCLUDE = "days";
    private static final String SNOWY_KEY = "snowy_days_count";
    private static final String SUNLIGHT_KEY = "sunlight_stats";

    @Inject
    @RestClient
    WeatherRestClient weatherRestClient;
    @Inject
    WeatherServiceConfig config;
    @Inject
    WeatherDayRepository weatherRepository;
    @Inject
    RedisService redisService;
    @Inject
    ObjectMapper objectMapper;

    @CacheResult(cacheName = "weather-cache")
    public WeatherResponse getWeather() {
        return weatherRestClient.getWeather(
                config.location(),
                config.apiKey(),
                INCLUDE,
                ELEMENTS
        );
    }

    public Long countSnowyDays() {
        var cachedResult = getFromRedis(SNOWY_KEY, Long.class);
        if (cachedResult.isPresent()) {
            log.info("Getting snowy days from Redis.");
            return cachedResult.get();
        }

        var result = weatherRepository.findLast30Days()
                .stream()
                .filter(day -> day.getSnow() != null && day.getSnow() > config.snow())
                .count();

        saveToRedis(SNOWY_KEY, result);
        log.info("Getting snowy days from Postgres.");
        return result;
    }


    public SunTimeStats calculateSunlightTimes() {
        var cachedStats = getFromRedis(SUNLIGHT_KEY, SunTimeStats.class);
        if (cachedStats.isPresent()) {
            log.info("Getting sun light from Redis.");
            return cachedStats.get();
        }

        var sunlightDurations = weatherRepository.findLast30Days().stream()
                .map(day -> Duration.between(day.getSunrise(), day.getSunset()))
                .toList();

        var min = sunlightDurations.stream().min(Duration::compareTo);
        var max = sunlightDurations.stream().max(Duration::compareTo);

        var average = sunlightDurations.stream()
                .reduce(Duration.ZERO, Duration::plus)
                .dividedBy(sunlightDurations.size());

        var stats = SunTimeStats.builder()
                .averageDuration(average)
                .minDuration(min.orElse(Duration.ZERO))
                .maxDuration(max.orElse(Duration.ZERO))
                .build();

        log.info("Getting sun light from Postgres.");
        saveToRedis(SUNLIGHT_KEY, stats);
        return stats;
    }

    private <T> Optional<T> getFromRedis(String key, Class<T> valueType) {
        var response = redisService.getValue(key);
        if (response != null) {
            try {
                return Optional.of(objectMapper.readValue(response, valueType));
            } catch (Exception e) {
                log.warn("Failed to parse cached value from Redis for key: {}", key);
            }
        }
        return Optional.empty();
    }

    private void saveToRedis(String key, Object value) {
        try {
            var json = objectMapper.writeValueAsString(value);
            redisService.saveValue(key, json);
        } catch (Exception e) {
            log.warn("Failed to save value to Redis for key: {}",key);
        }
    }
}
