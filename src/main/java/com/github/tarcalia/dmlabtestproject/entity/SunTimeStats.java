package com.github.tarcalia.dmlabtestproject.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Duration;

@Getter
@Setter
@ToString
@Builder
public class SunTimeStats {
    private Duration averageDuration;
    private Duration minDuration;
    private Duration maxDuration;
}
