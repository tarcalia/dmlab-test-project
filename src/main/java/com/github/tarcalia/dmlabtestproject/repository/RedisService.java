package com.github.tarcalia.dmlabtestproject.repository;

import io.quarkus.redis.client.RedisClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@ApplicationScoped
@Slf4j
public class RedisService {

    @Inject
    RedisClient redisClient;

    public void saveValue(String key, String value) {
        redisClient.set(List.of(key, value));
    }

    public String getValue(String key) {
        var response = redisClient.get(key);
        return response != null ? response.toString() : null;
    }
}
