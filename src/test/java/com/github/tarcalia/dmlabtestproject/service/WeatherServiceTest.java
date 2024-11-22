package com.github.tarcalia.dmlabtestproject.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tarcalia.dmlabtestproject.client.WeatherRestClient;
import com.github.tarcalia.dmlabtestproject.config.WeatherServiceConfig;
import com.github.tarcalia.dmlabtestproject.entity.SunTimeStats;
import com.github.tarcalia.dmlabtestproject.entity.WeatherResponse;
import com.github.tarcalia.dmlabtestproject.repository.RedisService;
import com.github.tarcalia.dmlabtestproject.repository.WeatherDayRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static com.github.tarcalia.dmlabtestproject.util.MockUtil.ELEMENTS;
import static com.github.tarcalia.dmlabtestproject.util.MockUtil.INCLUDE;
import static com.github.tarcalia.dmlabtestproject.util.MockUtil.SNOWY_KEY;
import static com.github.tarcalia.dmlabtestproject.util.MockUtil.SUNLIGHT_KEY;
import static com.github.tarcalia.dmlabtestproject.util.MockUtil.getFirstDay;
import static com.github.tarcalia.dmlabtestproject.util.MockUtil.getMockWeatherResponse;
import static com.github.tarcalia.dmlabtestproject.util.MockUtil.getSecondDay;
import static com.github.tarcalia.dmlabtestproject.util.MockUtil.getSunTimeStats;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class WeatherServiceTest {

    @Mock
    private WeatherRestClient weatherRestClient;

    @Mock
    private WeatherServiceConfig config;

    @Mock
    private WeatherDayRepository weatherRepository;

    @Mock
    private RedisService redisService;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private WeatherService weatherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetWeather() {
        // Given
        when(config.location()).thenReturn("Dublin");
        when(config.apiKey()).thenReturn("ApiKey");
        when(weatherRestClient.getWeather("Dublin", "ApiKey", INCLUDE, ELEMENTS))
                .thenReturn(getMockWeatherResponse());

        // When
        var result = weatherService.getWeather();

        // Then
        assertEquals(getMockWeatherResponse().getDays().size(), result.getDays().size());
        verify(weatherRestClient, times(1)).getWeather("Dublin", "ApiKey", INCLUDE, ELEMENTS);
    }

    @Test
    void testCountSnowyDays_FromRedis() throws JsonProcessingException {
        // Given
        when(redisService.getValue(SNOWY_KEY)).thenReturn("5");
        when(objectMapper.readValue("5", Long.class)).thenReturn(5L);

        // When
        var result = weatherService.countSnowyDays();

        // Then
        assertEquals(5L, result);
        verify(redisService, times(1)).getValue(SNOWY_KEY);
        verifyNoInteractions(weatherRepository);
    }

    @Test
    void testCountSnowyDays_FromRepository() {
        // Given
        when(redisService.getValue(SNOWY_KEY)).thenReturn(null);
        when(weatherRepository.findLast30Days()).thenReturn(List.of(getFirstDay(), getSecondDay()));
        when(config.snow()).thenReturn(1);

        // When
        var result = weatherService.countSnowyDays();

        // Then
        assertEquals(1L, result);
        verify(redisService, times(1)).getValue(SNOWY_KEY);
        verify(weatherRepository, times(1)).findLast30Days();
    }

    @Test
    void testCalculateSunlightTimes_FromRedis() throws Exception {
        // Given
        when(redisService.getValue(SUNLIGHT_KEY)).thenReturn("{\"averageDuration\":10,\"minDuration\":8,\"maxDuration\":12}");
        when(objectMapper.readValue(anyString(), eq(SunTimeStats.class))).thenReturn(getSunTimeStats());

        // When
        var result = weatherService.calculateSunlightTimes();

        // Then
        assertEquals(getSunTimeStats().getAverageDuration(), result.getAverageDuration());
        assertEquals(getSunTimeStats().getMaxDuration(), result.getMaxDuration());
        assertEquals(getSunTimeStats().getMinDuration(), result.getMinDuration());
        verify(redisService, times(1)).getValue(SUNLIGHT_KEY);
        verifyNoInteractions(weatherRepository);
    }

    @Test
    void testCalculateSunlightTimes_FromRepository() {
        // Given
        when(redisService.getValue(SUNLIGHT_KEY)).thenReturn(null);
        when(weatherRepository.findLast30Days()).thenReturn(List.of(getFirstDay(), getSecondDay()));

        // When
        var result = weatherService.calculateSunlightTimes();

        // Then
        assertEquals(getSunTimeStats().getAverageDuration(), result.getAverageDuration());
        verify(redisService, times(1)).saveValue(eq(SUNLIGHT_KEY), any());
    }
}