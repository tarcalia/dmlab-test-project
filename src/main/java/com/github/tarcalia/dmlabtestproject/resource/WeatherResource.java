package com.github.tarcalia.dmlabtestproject.resource;

import com.github.tarcalia.dmlabtestproject.entity.SunTimeStats;
import com.github.tarcalia.dmlabtestproject.entity.WeatherResponse;
import com.github.tarcalia.dmlabtestproject.service.WeatherService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.inject.Inject;

/**
 * REST endpoints to expose weather-related data.
 *
 */
@Path("/weather")
public class WeatherResource {

    @Inject
    WeatherService weatherService;

    @GET
    public WeatherResponse getWeather() {
        return weatherService.getWeather();
    }

    @GET
    @Path("/snowy")
    public Long getSnowyDays() {
        return weatherService.countSnowyDays();
    }

    @GET
    @Path("/sun")
    public SunTimeStats getSunLightTime() {
        return weatherService.calculateSunlightTimes();
    }
}