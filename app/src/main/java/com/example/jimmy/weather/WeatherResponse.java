package com.example.jimmy.weather;

import org.springframework.http.HttpStatus;

/**
 * Created by Jimmy on 1/27/2016.
 */
public class WeatherResponse {
    public String forecastDetails, todayWeather;
    public HttpStatus statusCode;
    public String codeError;
}
