package com.github.tarcalia.dmlabtestproject.scheduler;

import com.github.tarcalia.dmlabtestproject.entity.WeatherDay;
import com.github.tarcalia.dmlabtestproject.repository.WeatherDayRepository;
import com.github.tarcalia.dmlabtestproject.service.WeatherService;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class WeatherScheduler {
    @Inject
    WeatherService service;
    @Inject
    WeatherDayRepository repository;

    @Scheduled(every = "24h")
    void executeTask() {
        var response = service.getWeather();
        response.getDays()
                .forEach(this::saveToDb);
    }

    private void saveToDb(WeatherDay d) {
        var day = repository.findById(d.getDateTime());
        if (day.isEmpty()) {
            log.info("Day {} not present. Saving into database.", d.getDateTime());
            repository.save(d);
        }
    }
}
