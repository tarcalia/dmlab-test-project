package com.github.tarcalia.dmlabtestproject.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class WeatherResponse {

    @JsonProperty("queryCost")
    private Integer queryCost;

    @JsonProperty("latitude")
    private Double latitude;

    @JsonProperty("longitude")
    private Double longitude;

    @JsonProperty("resolvedAddress")
    private String resolvedAddress;

    @JsonProperty("address")
    private String address;

    @JsonProperty("timezone")
    private String timezone;

    @JsonProperty("tzoffset")
    private Double tzOffset;

    @JsonProperty("days")
    private List<WeatherDay> days;
}
