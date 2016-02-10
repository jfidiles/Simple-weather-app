package com.example.jimmy.weather;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by Jimmy on 1/27/2016.
 */
public class APIConnection {
    public static class GetForecastWeather extends AsyncTask<String, Void, WeatherResponse> {
        String codeError;
        HttpStatus statusCode;
        String forecastDetails, todayWeather;
        public onWeatherResponse delegate = null;
        @Override
        protected WeatherResponse doInBackground(String... params) {
            try {

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity entity = new HttpEntity(headers);

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
                    @Override
                    public void handleError(ClientHttpResponse response) throws IOException {
                        codeError = StreamUtils.copyToString(response.getBody(),
                                Charset.defaultCharset());

                        statusCode = response.getStatusCode();
                    }
                });
                String location = params[0];
                String degreesType = params[1];
                String urlForecast = "http://api.openweathermap.org/data/2.5/forecast/daily?q=" +
                        location + "&mode=json&units=" + degreesType +
                        "&cnt=7&appid=7709795ad3ac7ba2cfd14c8fa7b44f4d";
                String urlCurrent = "http://api.openweathermap.org/data/2.5/weather?q=" +
                        location + "&units=metric&appid=7709795ad3ac7ba2cfd14c8fa7b44f4d";
                ResponseEntity<String> forecastResponse = restTemplate.exchange(urlForecast,
                        HttpMethod.GET, entity, String.class);
                ResponseEntity<String> currentWeatherResponse = restTemplate.exchange(urlCurrent,
                        HttpMethod.GET, entity, String.class);
                /*
                   Since the API returns only 200 HttpStatus I`m checking only if
                   the status is 200. If the status code is anything
                 */

                if (forecastResponse.getStatusCode().value() < 299) {
                    statusCode = forecastResponse.getStatusCode();
                    forecastDetails = forecastResponse.getBody();
                    todayWeather = currentWeatherResponse.getBody();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            WeatherResponse getWeather = new WeatherResponse();
            getWeather.forecastDetails = forecastDetails;
            getWeather.todayWeather = todayWeather;
            getWeather.codeError = codeError;
            getWeather.statusCode = statusCode;
            return getWeather;
        }

        @Override
        protected void onPostExecute(WeatherResponse weatherResponse) {
            if (delegate != null)
                delegate.getWeather(weatherResponse);
            else
                Log.d("API delegate:", "was not assigned!");
        }
    }

    public static class GetTimeZone extends AsyncTask<String, Void, String> {
        HttpStatus statusCode;
        String timeZone;
        public onWeatherResponse delegate = null;
        @Override
        protected String doInBackground(String... params) {
            try {

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity entity = new HttpEntity(headers);

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
                    @Override
                    public void handleError(ClientHttpResponse response) throws IOException {
                        statusCode = response.getStatusCode();
                    }
                });
                String lng = params[0];
                String lat = params[1];

                String url = "http://api.geonames.org/timezoneJSON?lat=" + lat +
                        "&lng=" + lng + "&username=weatherApp";
                ResponseEntity<String> timeZoneResponse = restTemplate.exchange(url,
                        HttpMethod.GET, entity, String.class);
                /*
                   Since the API returns only 200 HttpStatus I`m checking only if
                   the status is 200. If the status code is anything
                 */

                if (timeZoneResponse.getStatusCode().value() < 299) {
                    statusCode = timeZoneResponse.getStatusCode();
                    return timeZoneResponse.getBody();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String timeZoneResponse) {
            if (delegate != null)
                delegate.getTimeZone(timeZoneResponse);
            else
                Log.d("API timeZone delegate:", "was not assigned!");
        }
    }


}
