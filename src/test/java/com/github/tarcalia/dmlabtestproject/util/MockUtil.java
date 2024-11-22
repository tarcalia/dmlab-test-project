package com.github.tarcalia.dmlabtestproject.util;

import com.github.tarcalia.dmlabtestproject.entity.SunTimeStats;
import com.github.tarcalia.dmlabtestproject.entity.WeatherDay;
import com.github.tarcalia.dmlabtestproject.entity.WeatherResponse;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class MockUtil {
    public static final String ELEMENTS = "tempmax,tempmin,temp,datetime,feelslike,snow,windspeed,sunrise,sunset";
    public static final String INCLUDE = "days";
    public static final String SNOWY_KEY = "snowy_days_count";
    public static final String SUNLIGHT_KEY = "sunlight_stats";

    public static WeatherDay getFirstDay() {
        var day = new WeatherDay();
        day.setDateTime(LocalDate.of(2024, 11, 22));
        day.setTempMax(10.5);
        day.setTempMin(3.2);
        day.setTempAvg(6.9);
        day.setFeelsLike(5.5);
        day.setSnow(1.2);
        day.setWindspeed(15.0);
        day.setSunrise(LocalTime.of(6, 30, 0));
        day.setSunset(LocalTime.of(17, 15, 0));
        return day;
    }

    public static WeatherDay getSecondDay() {
        var day = new WeatherDay();
        day.setDateTime(LocalDate.of(2024, 11, 23));
        day.setTempMax(12.3);
        day.setTempMin(4.5);
        day.setTempAvg(8.4);
        day.setFeelsLike(7.0);
        day.setSnow(1.0);
        day.setWindspeed(20.0);
        day.setSunrise(LocalTime.of(6, 45, 0));
        day.setSunset(LocalTime.of(17, 30, 0));
        return day;
    }

    public static SunTimeStats getSunTimeStats() {
        return SunTimeStats.builder()
                .averageDuration(Duration.ofHours(10).plusMinutes(45))
                .minDuration(Duration.ofHours(10))
                .maxDuration(Duration.ofHours(11).plusMinutes(30))
                .build();
    }

    public static WeatherResponse getMockWeatherResponse() {
        var response = new WeatherResponse();
        response.setQueryCost(1);
        response.setLatitude(53.3498);
        response.setLongitude(-6.2603);
        response.setResolvedAddress("Dublin, Ireland");
        response.setAddress("Dublin");
        response.setTimezone("Europe/Dublin");
        response.setTzOffset(1.0);
        response.setDays(getMockWeatherDays());
        return response;
    }

    private static List<WeatherDay> getMockWeatherDays() {
        return List.of(
                MockUtil.getFirstDay(),
                MockUtil.getSecondDay()
        );
    }
}
