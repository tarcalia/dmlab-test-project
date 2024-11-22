package com.github.tarcalia.dmlabtestproject.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity(name = "Day")
@Table(name = "t_day")
@NamedQuery(name = WeatherDay.FIND_BY_ID, query = """
    SELECT wd FROM Day wd WHERE wd.id = :param
    """)
@NamedQuery(name = WeatherDay.FIND_LAST_30_DAYS, query = """
    SELECT w FROM Day w WHERE w.dateTime <= :today ORDER BY w.dateTime DESC
    """
)
@ToString
public class WeatherDay extends PersistedEntity {
    public static final String FIND_BY_ID = "findByIdWeatherDay";
    public static final String FIND_LAST_30_DAYS = "findLast30Days";

    @Id
    @JsonProperty("datetime")
    private LocalDate dateTime;

    @Column(name = "tempmax")
    @JsonProperty("tempmax")
    private double tempMax;

    @Column(name = "tempmin")
    @JsonProperty("tempmin")
    private double tempMin;

    @Column(name = "temp")
    @JsonProperty("temp")
    private double tempAvg;

    @Column(name = "feelslike")
    @JsonProperty("feelslike")
    private double feelsLike;

    @Column
    @JsonProperty("snow")
    private Double snow;

    @Column(name = "windspeed")
    @JsonProperty("windspeed")
    private Double windspeed;

    @Column(name = "sunrise")
    @JsonProperty("sunrise")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime sunrise;

    @Column(name = "sunset")
    @JsonProperty("sunset")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime sunset;
}
