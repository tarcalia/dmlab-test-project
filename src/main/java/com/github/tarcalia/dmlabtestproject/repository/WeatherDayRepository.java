package com.github.tarcalia.dmlabtestproject.repository;

import com.github.tarcalia.dmlabtestproject.entity.WeatherDay;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class WeatherDayRepository extends GenericRepository<WeatherDay> {
    @Override
    protected Class<WeatherDay> getEntityClass() {
        return WeatherDay.class;
    }

    public List<WeatherDay> findLast30Days() {
        return em.createNamedQuery(WeatherDay.FIND_LAST_30_DAYS, WeatherDay.class)
                .setParameter("today", LocalDate.now())
                .setMaxResults(30)
                .getResultList();
    }
}
