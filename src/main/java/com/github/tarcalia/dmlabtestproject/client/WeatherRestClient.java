package com.github.tarcalia.dmlabtestproject.client;

import com.github.tarcalia.dmlabtestproject.entity.WeatherResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 * Register web client during startup, which
 * will be used to communicate with API of <a href="https://www.visualcrossing.com">VisualCrossing</a> website.
 */
@Path("/VisualCrossingWebServices/rest/services/timeline")
@RegisterRestClient(configKey = "com.github.tarcalia.dmlabtestproject.client.WeatherRestClient")
public interface WeatherRestClient {

    @GET
    @Path("/{location}/last30days")
    WeatherResponse getWeather(
            @PathParam("location") String location,
            @QueryParam("key") String apiKey,
            @QueryParam("include") String include,
            @QueryParam("elements") String elements
    );
}
